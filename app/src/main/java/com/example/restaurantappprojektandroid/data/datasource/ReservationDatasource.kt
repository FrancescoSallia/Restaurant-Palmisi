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
          R.drawable.restaurant_reservation_pic_three,
          R.drawable.restaurant_reservation_pic_four,
          R.drawable.restaurant_reservation_pic_five,
          R.drawable.restaurant_reservation_pic_six,
      )

    }

    // Liste der Allergene
    private val allergeneList = listOf(
        "Gluten = G",
        "Laktose = L",
        "Erdnüsse = E",
        "Soja = S",
        "Fisch = F",
        "Schalentiere = B",
        "Baumnüsse = W",
        "Sellerie = M",
        "Senf = SF",
        "Sesamsamen = U",
        "Sulfite = P",
        "Lupinen = Q",
        "Weichtiere = O",
        "Ei = K",
        "Milch = D"
    )



    fun loadAllergene(): List<String> {
        val allergeneList = listOf("G", "L", "E", "F", "S", "B", "W", "M", "SF", "U", "P", "Q", "O","K","D")
        val numberOfAllergenes = (3..4).random() // Zufällig 3 oder 4
        return allergeneList.shuffled().take(numberOfAllergenes)
    }

}
