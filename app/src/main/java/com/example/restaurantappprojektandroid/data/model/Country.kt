package com.example.restaurantappprojektandroid.data.model

import com.squareup.moshi.Json

data class Country(
    @Json(name = "strArea")
    val countryName: String
)
