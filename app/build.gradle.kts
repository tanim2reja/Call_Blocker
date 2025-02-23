import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    kotlin("kapt") // Kotlin Annotation Processor Plugin
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.info.callblocker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.info.callblocker"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.10.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.32.0")
    implementation ("androidx.navigation:navigation-compose:2.8.6") // Navigation
    implementation ("androidx.compose.material:material:1.7.7") // Material Design (for Drawer and BottomAppBar)

    //Google Font
    implementation ("androidx.compose.ui:ui-text-google-fonts:1.7.7")

    // Coil Image Loading
    implementation ("io.coil-kt:coil-compose:2.5.0")
    implementation ("io.coil-kt:coil-gif:2.3.0")

    // hilt DI
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation ("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")

    // Optional: Lifecycle/ViewModel integration
  //  implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    // For instrumentation tests
    androidTestImplementation  ("com.google.dagger:hilt-android-testing:2.52")

    // For local unit tests
    testImplementation ("com.google.dagger:hilt-android-testing:2.52")

    // Room Database

    implementation("androidx.room:room-runtime:2.6.1") // Room runtime
    implementation("androidx.room:room-ktx:2.6.1")    // Room KTX
    kapt("androidx.room:room-compiler:2.6.1")         // Room compiler

    implementation ("androidx.core:core-telecom:1.0.0-beta01")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
kapt {
    correctErrorTypes = true
}

