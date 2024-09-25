package com.example.restaurantappprojektandroid.data.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    var id: String = "",
    val vorname: String  = "",
    val nachname: String = "",
    val likedGerichte: MutableList<Meal> = mutableListOf(),
    val profilbild:String? = ""
)
