plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    //Google services Gradle
    id("com.google.gms.google-services")

}

android {
    defaultConfig{

        vectorDrawables.useSupportLibrary = true
    }
    namespace = "com.example.restuarantprojektapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.restuarantprojektapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.legacy.support.v4)
    val retrofitVersion = "2.9.0"
    val roomVersion = "2.6.0"


    //Retrofit und Moshi
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

    // Coil
    implementation("io.coil-kt:coil:2.7.0")

    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    //Logger
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //Glide -> Gift Image Loader
    implementation("com.github.bumptech.glide:glide:4.12.0")

    //Lotties GIF
    implementation("com.airbnb.android:lottie:6.1.0")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    implementation("com.google.firebase:firebase-analytics")

  //Firebase Cloud
    implementation ("com.google.firebase:firebase-firestore-ktx")

    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

// Material Design ShapeableImageView (Titelbild)
    implementation ("com.google.android.material:material:1.5.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

//  val retrofitVersion = "2.9.0"
//      val roomVersion = "2.6.0"

