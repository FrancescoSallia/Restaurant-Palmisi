package com.example.restaurantappprojektandroid.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantappprojektandroid.remote.MealdbApi
import com.example.restaurantappprojektandroid.remote.Repository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repository = Repository(MealdbApi)

    val repositoryCategory = repository.category
    val repositoryMeals = repository.meals
    val repositoryMealDetail = repository.mealDetail

    fun getCategories() {
        viewModelScope.launch {

            repository.getCategories()
        }
    }

    fun getMealsByCategory(categorieName: String) {
        viewModelScope.launch {

            repository.getMealsByCategory(categorieName)
        }
    }

    //diese funktion muss noch im fragmentDetail stehen was erstellt werden muss!!
    fun getMealById(mealId: String) {
        viewModelScope.launch {

            repository.getMealById(mealId)

        }
}
}