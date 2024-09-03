package com.example.restaurantappprojektandroid.model

import com.squareup.moshi.Json

data class Category(
    val idCategory: String,
    @Json(name = "categorieName")
    val strCategory: String,
    val strCategoryDescription: String,
    @Json(name = "categorieImg")
    val strCategoryThumb: String
)