plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlin.native.cocoapods")
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvmToolchain(17)

    cocoapods {
        summary = "WakeOnLanApp shared module"
        homepage = "https://github.com/MaksymLeiber/WakeOnLanApp"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "sharedKit"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.stdlib)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting
        val iosMain by getting
    }
}

android {
    namespace = "com.banny.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}