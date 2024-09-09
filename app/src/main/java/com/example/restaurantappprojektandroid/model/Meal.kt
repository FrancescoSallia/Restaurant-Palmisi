package com.example.restaurantappprojektandroid.model

import android.util.Log
import com.squareup.moshi.Json

data class Meal(
    var idMeal: String = "",
    @Json(name = "strMeal")
    var mealName: String = "",
    @Json(name = "strMealThumb")
    var mealImg: String = ""
) {

    constructor(map: Map<String, Any>):this(){
        idMeal = map["idMeal"] as String
        mealName = map["mealName"] as String
        mealImg = map["mealImg"] as String

    }

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
            "mealImg" to mealImg
        )
    }
}



