package com.example.restaurantappprojektandroid.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

data class Reservation(
    @DocumentId
    val reservationId: String = "",
    val datum: String = "",
    val gaeste :Int = 0,
    val kommentarGast: String = "",
    val imgId : Int = 0
)

{

//    val datumFormatiert :String
//    get() = datum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
//    constructor(data: Map<String, Any>) : this(
//        reservationId = data["reservationId"] as String,
//        datum = (data["datum"] as String),
//        gaeste = (data["gaeste"]as Long).toInt(),
//        kommentarGast = data["kommentarGast"] as String
//    )
//
//
//    fun dataToMap(): Map<String, Any> {
//        return mapOf(
//
//            "reservationId" to reservationId,
//            "datum" to datum,
//            "gaeste" to gaeste,
//            "kommentarGast" to kommentarGast
//        )
//    }

}
