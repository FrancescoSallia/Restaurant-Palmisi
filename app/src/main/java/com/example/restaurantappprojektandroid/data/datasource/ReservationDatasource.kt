package com.example.restaurantappprojektandroid.data.datasource

import kotlin.math.min

class ReservationDatasource() {

    fun loadAnzahlAnGaesten(): List<Int> {
        return (0..20).toList()
    }

    fun loadUhrzeiten(openingHours: Int, closingHours: Int): List<String> {
        val times = mutableListOf<String>()

        (openingHours..<closingHours).forEach { hour ->
            (0..3).forEach { minutes ->
                var timeString = ""
                if (hour < 10) timeString += "0"
                timeString += "$hour:"
                val actualMinutes = 15 * minutes
                if(minutes == 0) timeString += "0"
                timeString += actualMinutes.toString()
                times.add(timeString)
            }
        }
        return times
    }
}