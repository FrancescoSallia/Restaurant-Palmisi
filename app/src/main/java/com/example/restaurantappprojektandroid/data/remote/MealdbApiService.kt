package com.example.restaurantappprojektandroid.data.remote

import com.example.restaurantappprojektandroid.data.model.CategorieResponse
import com.example.restaurantappprojektandroid.data.model.MealResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Die Basis-URL für alle API-Endpunkte der TheMealDB (muss mit "/" enden)
const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

// Erstellt einen HTTP-Logger, um Netzwerkaufrufe zu protokollieren (zum Beispiel in Logcat sichtbar)
private val logger = HttpLoggingInterceptor().apply {
    // Setzt das Logging-Level auf BODY, um vollständige Request- und Response-Daten zu sehen
    level = HttpLoggingInterceptor.Level.BODY
}
// Erstellt einen OkHttpClient und hängt den Logger als Interceptor ein
private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

// Erstellt eine Moshi-Instanz (JSON-Parser für Kotlin)
// Die KotlinJsonAdapterFactory sorgt dafür, dass Kotlin-Datenklassen korrekt verarbeitet werden
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Baut die Retrofit-Instanz:
// - mit Moshi als JSON-Konverter
// - mit der Basis-URL
// - und dem vorher konfigurierten OkHttpClient (inkl. Logging)
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface MealdbApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategorieResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") categorieName: String): MealResponse

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") mealId: String): MealResponse

    @GET("search.php")
    suspend fun searchMeal(@Query("s") mealName: String): MealResponse
}

object MealdbApi {
    val retrofitService: MealdbApiService by lazy { retrofit.create(MealdbApiService::class.java) }
}