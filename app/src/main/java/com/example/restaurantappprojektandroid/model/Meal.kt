package com.example.restaurantappprojektandroid.model

import com.squareup.moshi.Json

data class Meal(
    val idMeal: String,
    @Json(name = "strMeal")
    val mealName: String,
    @Json(name = "strMealThumb")
    val mealImg: String
)