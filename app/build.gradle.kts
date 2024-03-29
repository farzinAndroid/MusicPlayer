plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.farzin.musicplayer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.farzin.musicplayer"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
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
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //hilt di
    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //compose navigation
    implementation ("androidx.navigation:navigation-compose:2.7.7")

    //room
    implementation ("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")

    //animation
    implementation ("com.airbnb.android:lottie-compose:6.3.0")

    //coil - load image from url
    implementation ("io.coil-kt:coil-compose:2.2.2")

    // This dependency contains all the material icons
    implementation("androidx.compose.material:material-icons-extended:1.6.3")

    //system ui controller
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

    //media 3
    implementation("androidx.media3:media3-exoplayer:1.3.0")
    implementation("androidx.media3:media3-ui:1.3.0")
    implementation("androidx.media3:media3-datasource-okhttp:1.3.0")
    implementation("androidx.media3:media3-session:1.3.0")
    // Needed MediaSessionCompat.Token
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")

    // permission
    implementation("com.google.accompanist:accompanist-permissions:0.30.0")

    // palette api
    implementation("androidx.palette:palette-ktx:1.0.0")

    // custom slider
//    implementation("ir.mahozad.multiplatform:wavy-slider:1.1.0")
    implementation ("com.github.lincollincol:compose-audiowaveform:1.1.1")

    // amplitude
    implementation ("com.github.lincollincol:amplituda:2.2.2")

    //datastore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

}