package com.example.restaurantappprojektandroid.data.model

import com.squareup.moshi.Json

data class CountryMeal(
    val idMeal: String,
    @Json(name = "strMeal")
    val areaMealName: String,
    @Json(name = "strMealThumb")
    val areaMealImg: String
)