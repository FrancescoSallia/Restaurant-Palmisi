package com.example.restaurantappprojektandroid.datasource

class ReservationDatasource() {

    fun loadAnzahlAnGaesten(): List<Int> {
        return listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
    }

    fun loadUhrzeiten(startTime:Int, endTime:Int): List<String> {

            val uhrzeiten = mutableListOf<String>()

            (startTime..endTime).forEach { hour ->
                    (0..3).forEach { minutes ->
                            var viertelStunde = minutes * 15
                            if (minutes == 0){
//                                var minutenString = "0"
                            }
                           val time = "$hour:$minutes"
                            uhrzeiten.add(time)
                    }
            }
            return uhrzeiten
    }
}