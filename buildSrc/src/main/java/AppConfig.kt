import org.gradle.api.JavaVersion

object AppConfig {
    const val version = "1.0.0"
    const val versionCode = 1
    const val jvmTarget = "17"

    object Java {
        val version = JavaVersion.VERSION_17
    }

    object KotlinCompiler {
        val version = "1.4.7"
    }

    object Android {
        const val minSdk = 21
        const val compileSdk = 34
        const val targetSdk = 34
    }
}