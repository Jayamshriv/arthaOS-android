plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
//    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.jayam.arthaos"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.jayam.arthaos"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // C++ / NDK configuration
//        externalNativeBuild {
//            cmake {
//                cppFlags += "-std=c++17"
//                arguments += "-DANDROID_STL=c++_shared"
//            }
//        }
//
//        ndk {
//            // Target the ABIs you care about
//            // armeabi-v7a covers older devices, arm64-v8a covers all modern phones
//            abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86_64")
//        }
    }

    // Link CMakeLists.txt for the native SMS parser
//    externalNativeBuild {
//        cmake {
//            path = file("src/main/cpp/CMakeLists.txt")
//            version = "3.22.1"
//        }
//    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }

    android {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

//        kotlinOptions {
//            jvmTarget = "17"
//        }
    }
    kotlin {
        jvmToolchain(17)
    }



    buildFeatures {
        compose = true
        buildConfig = true
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.camera.view)
    // ── Core ──────────────────────────────────────────────────────────────
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    coreLibraryDesugaring(libs.android.desugar.jdk)

    // ── Compose ───────────────────────────────────────────────────────────
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.compose.animation)
    implementation(libs.coil.compose)


    // ── Navigation ────────────────────────────────────────────────────────
    implementation(libs.androidx.navigation.compose)

    // ── Lifecycle + ViewModel ─────────────────────────────────────────────
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // ── Hilt (DI) ─────────────────────────────────────────────────────────
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)                    // was: kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

// ── Room (local database) ─────────────────────────────────────────────
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)           // was: kapt(libs.androidx.room.compiler)

// ── WorkManager ─────────────────────────────────────────────────────
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)           // was: kapt(libs.androidx.hilt.compiler) — duplicate of hilt.compiler above, keep both as ksp
    // ── DataStore (settings/preferences) ──────────────────────────────────
    implementation(libs.androidx.datastore.preferences)

    // ── Coroutines ────────────────────────────────────────────────────────
    implementation(libs.kotlinx.coroutines.android)

    // ── CameraX (receipt scanner) ─────────────────────────────────────────
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.compose)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.camera2)

    // ── ML Kit (OCR for receipts) ─────────────────────────────────────────
    implementation(libs.play.services.mlkit.text.recognition)

    // ── Biometric ─────────────────────────────────────────────────────────
    implementation(libs.androidx.biometric)

//    // ── WorkManager (background tasks, widget refresh) ────────────────────
//    implementation(libs.androidx.work.runtime.ktx)
//    implementation(libs.androidx.hilt.work)
//    kapt(libs.androidx.hilt.compiler)

    // ── Glance (home screen widget) ───────────────────────────────────────
//    implementation(libs.androidx.glance.appwidget)
//    implementation(libs.androidx.glance.material3)

    // ── Charts (Vico — Compose-native) ────────────────────────────────────
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)

    // ── Firebase (optional backup — add google-services plugin when ready) ─
    // implementation(platform(libs.firebase.bom))
    // ── Testing ───────────────────────────────────────────────────────────
    // implementation(libs.firebase.firestore.ktx)
    // implementation(libs.firebase.auth.ktx)
    implementation(libs.kotlinx.serialization.json)

    // ── Permissions ───────────────────────────────────────────────────────────
    implementation(libs.accompanist.permissions)

    // ── Testing ───────────────────────────────────────────────────────────
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    configurations.all {
        resolutionStrategy.force("androidx.tracing:tracing:1.2.0")
    }
    debugImplementation(libs.androidx.ui.test.manifest)
}