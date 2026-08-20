package com.jayam.artha_os.feature.receipt_ocr.vm

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.compose.ui.geometry.Offset
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    private var surfaceMeteringPointFactory: SurfaceOrientedMeteringPointFactory? = null
    private var cameraControl: CameraControl? = null
    private val imageCapture = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
        .build()
    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    val capturedImageUri = _capturedImageUri.asStateFlow()

    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            _surfaceRequest.update { newSurfaceRequest }
            surfaceMeteringPointFactory = SurfaceOrientedMeteringPointFactory(
                newSurfaceRequest.resolution.width.toFloat(),
                newSurfaceRequest.resolution.height.toFloat()
            )
        }
    }

    suspend fun bindToCamera(
        appContext: Context,
        lifecycleOwner: LifecycleOwner
    ) {
        val provider = ProcessCameraProvider.awaitInstance(appContext)

        provider.unbindAll()

        val camera = provider.bindToLifecycle(
            lifecycleOwner,
            DEFAULT_BACK_CAMERA,
            cameraPreviewUseCase,
            imageCapture
        )

        cameraControl = camera.cameraControl

        try {
            awaitCancellation()
        } finally {
            provider.unbindAll()
            cameraControl = null
        }
    }

    fun tapToFocus(tapCoords: Offset) {
        val point = surfaceMeteringPointFactory?.createPoint(tapCoords.x, tapCoords.y)
        if (point != null) {
            val meteringAction = FocusMeteringAction.Builder(point).build()
            cameraControl?.startFocusAndMetering(meteringAction)
        }
    }

    private val recognizer by lazy {
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    fun retake() {

        _capturedImageUri.value?.path?.let {
            File(it).delete()
        }

        _capturedImageUri.value = null
    }

    fun captureImage(context: Context) {

        val file = File.createTempFile(
            "receipt_",
            ".jpg",
            context.cacheDir
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    _capturedImageUri.value = file.toUri()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("Camera", "Capture failed", exception)
                }
            }
        )
    }

    fun scanReceipt(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->

                Log.d("OCR", visionText.text)

                visionText.textBlocks.forEach { block ->

                    Log.d("OCR", "Block = ${block.text}")

                    block.lines.forEach { line ->

                        Log.d("OCR", "Line = ${line.text}")

                        line.elements.forEach { element ->

                            Log.d("OCR", "Word = ${element.text}")
                        }
                    }
                }
            }
            .addOnFailureListener {

                Log.e("OCR", "Recognition failed", it)
            }
    }

    fun scanReceipt(uri: Uri) {

        val image = InputImage.fromFilePath(context, uri)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->

                Log.d("OCR ", "----------------------------------------------")
                Log.d("OCR ", visionText.text)
                Log.d("OCR ", "----------------------------------------------")

//                visionText.textBlocks.forEach { block ->
//
//                    Log.d("OCR", "Block = ${block.text}")
//
//                    block.lines.forEach { line ->
//
//                        Log.d("OCR", "Line = ${line.text}")
//
//                        line.elements.forEach { element ->
//
//                            Log.d("OCR", "Word = ${element.text}")
//                        }
//                    }
//                }
                deleteTempImage(uri)
            }
            .addOnFailureListener {

                deleteTempImage(uri)
            }
    }
    private fun deleteTempImage(uri: Uri) {

        uri.path?.let {
            File(it).delete()
        }

        _capturedImageUri.value = null
    }

    fun imageProxyToBitmap(image: ImageProxy): Bitmap? {
        val planeProxy = image.planes

        val yBuffer = planeProxy[0].buffer
        val uBuffer = planeProxy[1].buffer
        val vBuffer = planeProxy[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = YuvImage(
            nv21,
            ImageFormat.NV21,
            image.width,
            image.height,
            null
        )

        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(
            Rect(0, 0, image.width, image.height),
            100,
            out
        )

        val bytes = out.toByteArray()

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    override fun onCleared() {
        recognizer.close()
        super.onCleared()
    }
}