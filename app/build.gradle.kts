plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.kinopoiskapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kinopoiskapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    val viewBinding = "1.5.9"
    implementation("com.github.kirich1409:viewbindingpropertydelegate:$viewBinding")

    val navigation = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigation")
    implementation("androidx.navigation:navigation-ui-ktx:$navigation")

    val coroutines = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")

    val serialization = "1.6.2"
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")

    val okHttp = "4.12.0"
    implementation("com.squareup.okhttp3:okhttp:$okHttp")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttp")

    val retrofit = "2.9.0"
    val retrofitConverter = "1.0.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$retrofitConverter")

    val glide = "4.14.2"
    implementation("com.github.bumptech.glide:glide:$glide")
    ksp("com.github.bumptech.glide:ksp:$glide")

    val room = "2.6.1"
    implementation("androidx.room:room-ktx:$room")
    implementation("androidx.room:room-runtime:$room")
    ksp("androidx.room:room-compiler:$room")
}