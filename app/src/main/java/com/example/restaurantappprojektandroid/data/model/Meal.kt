package com.example.restaurantappprojektandroid.data.model

import com.squareup.moshi.Json

data class Meal(
    var idMeal: String = "",
    @Json(name = "strMeal")
    var mealName: String = "",
    @Json(name = "strMealThumb")
    var mealImg: String = "",
    var liked: Boolean = false

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

    fun toMap():Map<String, Any> {
        return mapOf(
            "idMeal" to idMeal,
            "mealName" to mealName,
            "mealImg" to mealImg,
            "liked" to liked
        )
    }
}



