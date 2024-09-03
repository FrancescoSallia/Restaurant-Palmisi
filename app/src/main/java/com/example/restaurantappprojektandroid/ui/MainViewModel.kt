package com.example.restaurantappprojektandroid.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantappprojektandroid.remote.MealdbApi
import com.example.restaurantappprojektandroid.remote.Repository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(MealdbApi)

      lateinit var selectedMealId : String

    val repositoryCategory = repository.category
    val repositoryMeals = repository.meals
    val repositoryMealDetail = repository.mealDetail

    fun getCategories() {
        viewModelScope.launch {

            repository.getCategories()
        }
    }

    fun getMealsByCategory(categorieName: String) {
        Log.i("INFO", "getMealsByCategory aufgerufen")

        viewModelScope.launch {

            repository.getMealsByCategory(categorieName)
        }
    }

    fun getMealById(mealId: String) {
        viewModelScope.launch {
             selectedMealId = mealId
            repository.getMealById(selectedMealId)
        }
    }


}