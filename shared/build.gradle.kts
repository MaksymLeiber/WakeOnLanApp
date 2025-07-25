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

    cocoapods {
        summary = "WakeOnLanApp shared module"
        homepage = "https://github.com/MaksymLeiber/WakeOnLanApp"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "sharedKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        androidMain {
            dependencies {
            }
        }
        iosMain {
            dependencies {
            }
        }
    }
}