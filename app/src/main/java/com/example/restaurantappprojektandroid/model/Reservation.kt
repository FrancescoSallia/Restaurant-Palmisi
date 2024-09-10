package com.example.restaurantappprojektandroid.model

import com.google.firebase.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class Reservation(
    val reservationId: String = "",
    val datum: LocalDateTime,
    val gaeste :Int = 0,
    val kommentarGast: String = ""
)

{

    val datumFormatiert :String
    get() = datum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    constructor(data: Map<String, Any>) : this(
        reservationId = data["reservationId"] as String,
        datum = (data["datum"] as Timestamp).toLocalDateTime(),
        gaeste = (data["gaeste"]as Long).toInt(),
        kommentarGast = data["kommentarGast"] as String
    )

    companion object {
        fun Timestamp.toLocalDateTime(): LocalDateTime {
            return LocalDateTime.ofInstant(this.toDate().toInstant(), ZoneId.systemDefault())
        }
    }
}
