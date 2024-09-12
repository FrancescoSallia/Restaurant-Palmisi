package com.example.restaurantappprojektandroid.data.model

import com.squareup.moshi.Json

data class Category(
    val idCategory: String,
    @Json(name = "strCategory")
    val categorieName: String,
    val strCategoryDescription: String,
    @Json(name = "strCategoryThumb")
    val categorieImg: String
)