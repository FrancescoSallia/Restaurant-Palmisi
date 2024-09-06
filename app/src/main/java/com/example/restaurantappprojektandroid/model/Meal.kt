package com.example.restaurantappprojektandroid.model

import android.util.Log
import com.squareup.moshi.Json

data class Meal(
    val idMeal: String = "",
    @Json(name = "strMeal")
    val mealName: String = "",
    @Json(name = "strMealThumb")
    val mealImg: String = ""
) {


    val price: Double
        get() = randomPrice()

    val priceasString: String
        get() = price.toString() + "€"

    fun randomPrice(): Double {
        val euro = (1..9).random()
        val cent = (0..99).random()
        return "$euro.$cent".toDouble()
    }
}

