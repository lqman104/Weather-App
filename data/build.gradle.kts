plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.luqman.weather.data"
    compileSdk = AppConfig.Android.compileSdk

    defaultConfig {
        minSdk = AppConfig.Android.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.Java.version
        targetCompatibility = AppConfig.Java.version
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(AndroidDependencies.AndroidX.CoreKtx.plugin)

    // NETWORK
    implementation(AndroidDependencies.Network.Retrofit.plugin)
    implementation(AndroidDependencies.Network.Retrofit.converterMoshi)

    implementation(AndroidDependencies.Json.Moshi.plugin)
    kapt(AndroidDependencies.Json.Moshi.codegen)

    // DATABASE
    implementation(AndroidDependencies.AndroidX.Room.runtime)
    implementation(AndroidDependencies.AndroidX.Room.ktx)
    kapt(AndroidDependencies.AndroidX.Room.compiler)

    implementation(AndroidDependencies.Network.OkHttp.logging)

    implementation(AndroidDependencies.Logger.Timber.plugin)

    implementation(AndroidDependencies.Kotlin.Coroutine.plugin)

    // DI
    implementation(AndroidDependencies.Di.Hilt.plugin)
    kapt(AndroidDependencies.Di.Hilt.compiler)

    testImplementation(AndroidDependencies.Test.JUnit.plugin)
}