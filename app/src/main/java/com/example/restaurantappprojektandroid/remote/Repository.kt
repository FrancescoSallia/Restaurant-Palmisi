package com.example.restaurantappprojektandroid.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantappprojektandroid.model.Category
import com.example.restaurantappprojektandroid.model.Meal

class Repository(private val api: MealdbApi) {


    private val _categorie = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>>
        get() = _categorie

    private val _meals = MutableLiveData<List<Meal>>()
    val meals : LiveData<List<Meal>>
        get() = _meals


    suspend fun getCategories(){
        try {
            val result = api.retrofitService.getCategories()
            _categorie.postValue(result.categories)
        } catch (e: Exception) {
            Log.i("INFO","schau im Repository nach bei getCategories")
        }
    }

    suspend fun getMealsByCategory(categorieName: String) {
        try {
            val result = api.retrofitService.getMealsByCategory(categorieName)
            _meals.postValue(result.meals)
        } catch (e: Exception) {
            Log.i("INFO", "schau im Repository nach bei getMealsByCategory : $e")
        }
    }



}