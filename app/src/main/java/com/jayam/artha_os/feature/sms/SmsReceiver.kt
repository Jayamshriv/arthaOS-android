package com.jayam.artha_os.feature.sms

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Telephony
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SmsReceiver : BroadcastReceiver() {


    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {
        Log.e("message_message",intent?.action.toString())
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(context).notify(
            999,
            NotificationCompat.Builder(context, "debug")
                .setContentTitle("Fired")
                .build()
        )
        if (intent?.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
            return

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val entryPoint = EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    SmsReceiverEntryPoint::class.java
                )

                val processor = entryPoint.smsProcessor()

                Telephony.Sms.Intents
                    .getMessagesFromIntent(intent)
                    .forEach {
                        Log.e("message_message",it.toString())
                        processor.process(
                            sender = it.displayOriginatingAddress.orEmpty(),
                            body = it.messageBody.orEmpty(),
                            timestamp = it.timestampMillis
                        )
                    }

            } catch(e: Exception){
                e.printStackTrace()
            } finally {
                pendingResult.finish()
            }
        }
    }
}