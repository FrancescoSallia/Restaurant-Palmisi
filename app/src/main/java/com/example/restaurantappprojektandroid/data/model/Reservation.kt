package com.example.restaurantappprojektandroid.data.model

import android.content.Context
import android.graphics.Color
import com.example.restuarantprojektapp.R
import com.google.firebase.firestore.DocumentId
import kotlin.random.Random

data class Reservation(
    @DocumentId
    val reservationId: String = "",
    val datum: String = "",
    val gaeste: Int = 0,
    val kommentarGast: String = "",
    val imgId: Int = 0,
    val approved: Boolean = Random.nextBoolean()
) {
    fun getApprovedReservation(context: Context): ReservationItem {
        return if (approved) {
            ReservationItem("Bestätigt",context.getColor(R.color.green))
        } else {
            ReservationItem("in Bearbeitung", Color.RED)
        }
    }
}
class ReservationItem(
    val name:String,
    val color: Int
)
