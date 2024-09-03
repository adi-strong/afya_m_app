plugins {
  alias(libs.plugins.android.application)
}

android {
  namespace = "com.bop.shafya"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.bop.shafya"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  buildFeatures {
    viewBinding = true
  }
  externalNativeBuild {
    cmake {
      path = file("src/main/cpp/CMakeLists.txt")
      version = "3.22.1"
    }
  }
}

dependencies {

  implementation(libs.material.v180)
  implementation(libs.cardview)
  implementation(libs.retrofit)
  implementation(libs.converter.gson)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.java.jwt)
  implementation(libs.appcompat)
  implementation(libs.material)
  implementation(libs.constraintlayout)
  implementation(libs.lifecycle.livedata.ktx)
  implementation(libs.lifecycle.viewmodel.ktx)
  implementation(libs.navigation.fragment)
  implementation(libs.navigation.ui)
  implementation(libs.annotation)
  implementation(libs.activity)
  implementation(files("libs/core-3.2.1.jar"))
  implementation(files("libs/emv_2.0.0_R240607.jar"))
  implementation(files("libs/SmartPos_1.9.0_R240612.jar"))
  implementation(files("libs/nextbiometrics-devices-android.jar"))
  implementation(files("libs/jna.jar"))
  testImplementation(libs.junit)
  androidTestImplementation(libs.ext.junit)
  androidTestImplementation(libs.espresso.core)
}