package com.example.restaurantappprojektandroid.data.datasource

class AllergenDatasource() {

    fun loadAllergene(): List<String> {
        val allergeneList =
            listOf("G", "L", "E", "F", "S", "B", "W", "M", "SF", "U", "P", "Q", "O", "K", "D")
        val numberOfAllergenes = (3..4).random() // Zufällig 3 oder 4
        return allergeneList.shuffled().take(numberOfAllergenes)
    }
}