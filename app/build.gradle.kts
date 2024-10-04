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

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java) {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf()
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {


    //Retrofit und Moshi
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)

    // Coil
    implementation(libs.coil)

    //Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Logger
    implementation(libs.logging.interceptor)

    //Glide -> Gift Image Loader
    implementation(libs.glide)

    //Lotties GIF
    implementation(libs.lottie)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.legacy.support.v4)

  //Firebase Cloud
    implementation (libs.firebase.firestore.ktx)

    //Firebase Storage
    implementation(libs.firebase.storage)

    //RecyclerView
    implementation(libs.androidx.recyclerview)

// Material Design ShapeableImageView (Titelbild)
    implementation (libs.material.v150)

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

