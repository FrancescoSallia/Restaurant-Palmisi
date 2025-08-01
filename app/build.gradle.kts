import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.restuarantprojektapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.restuarantprojektapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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

    buildFeatures {
        viewBinding = true
    }
}

// Nur noch das neue Kotlin 2.x DSL verwenden!
tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11) // Du nutzt Java 11 – korrekt so
        freeCompilerArgs.addAll(listOf()) // ggf. eigene Compiler-Flags hier eintragen
    }
}

dependencies {
    // Retrofit + Moshi: für API-Abfragen und JSON-Parsing
    implementation(libs.retrofit)               // HTTP-Client (Retrofit)
    implementation(libs.converter.moshi)        // Retrofit Moshi-Converter
    implementation(libs.moshi.kotlin)           // JSON-Parsing mit Moshi und Kotlin-Unterstützung

    // Coil: Bild-Loading für ImageViews
    implementation(libs.coil)

    // OkHttp Logging: zum Protokollieren von HTTP-Requests/Responses
    implementation(libs.logging.interceptor)

    // Glide: Alternative zu Coil – v.a. für animierte oder größere Bilder (z.B. GIFs)
    implementation(libs.glide)

    // Lottie: für animierte JSON-Grafiken (z.B. Lade-Animationen)
    implementation(libs.lottie)

    // Firebase (über BOM = Bill of Materials)
    implementation(platform(libs.firebase.bom))   // Zentrale Versionsverwaltung für Firebase
    implementation(libs.firebase.firestore.ktx)     // Firestore Kotlin-Erweiterungen
    implementation(libs.firebase.analytics)         // Firebase Analytics
    implementation(libs.firebase.auth.ktx)          // Firebase Authentication
    implementation(libs.firebase.database.ktx)      // Echtzeitdatenbank
    implementation(libs.firebase.firestore)         // Firestore Datenbank
    implementation(libs.firebase.storage)           // Firebase Storage für z.B. Bilder

    // Legacy Support für alte Android-Komponenten
    implementation(libs.androidx.legacy.support.v4)

    // RecyclerView: für Listen und Grid-Darstellungen
    implementation(libs.androidx.recyclerview)

    // Material Design Komponenten (z.B. ShapeableImageView)
    implementation(libs.material.v150)

    // AndroidX Core & UI Libraries
    implementation(libs.androidx.core.ktx)                 // Kotlin-Erweiterungen für AndroidX
    implementation(libs.androidx.appcompat)                // Unterstützung für AppCompat-Features
    implementation(libs.material)                          // Material Components
    implementation(libs.androidx.activity)                 // Activity-Komponenten
    implementation(libs.androidx.constraintlayout)         // ConstraintLayout für flexible UI-Layouts
    implementation(libs.androidx.navigation.fragment.ktx)  // Jetpack Navigation – Fragment
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)        // Jetpack Navigation – UI-Bindings

    // Test-Bibliotheken
    testImplementation(libs.junit)                         // Unit-Testing
    androidTestImplementation(libs.androidx.junit)         // AndroidX Unit-Testing
    androidTestImplementation(libs.androidx.espresso.core) // UI-Testing mit Espresso
}

// Optional: Room (auskommentiert lassen oder aktivieren)
// implementation(libs.androidx.room.runtime)
// kapt(libs.androidx.room.compiler)
// implementation(libs.androidx.room.ktx)
