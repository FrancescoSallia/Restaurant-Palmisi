package com.example.restaurantappprojektandroid.data.datasource

import com.example.restuarantprojektapp.R

class ReservationDatasource() {

    fun loadAnzahlAnGaesten(): List<Int> {
        return (1..20).toList()
    }


    fun loadTimes(start: Int, end: Int): List<String> {
        val zeit = mutableListOf<String>()
        for (i in start..end) {
            if (i < 10) {
                zeit.add("0$i:00")
                zeit.add("0$i:15")
                zeit.add("0$i:30")
                zeit.add("0$i:45")
            } else {
                zeit.add("$i:00")
                zeit.add("$i:15")
                zeit.add("$i:30")
                zeit.add("$i:45")
            }
        }
        return zeit

    }

    fun loadRandomPictures(): List<Int> {

      return listOf(
          R.drawable.restaurant_reservation_pic_one,
          R.drawable.restaurant_reservation_pic_two,
          R.drawable.restaurant_reservation_pic_three
      ).shuffled()

    }
}