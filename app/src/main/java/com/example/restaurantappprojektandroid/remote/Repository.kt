package com.example.restaurantappprojektandroid.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantappprojektandroid.model.Category
import com.example.restaurantappprojektandroid.model.Meal
import com.example.restaurantappprojektandroid.model.MealX

class Repository(private val api: MealdbApi) {


    private val _categorie = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>>
        get() = _categorie

    private val _meals = MutableLiveData<List<Meal>>()
    val meals : LiveData<List<Meal>>
        get() = _meals

    private val _mealDetail = MutableLiveData<List<MealX>>()
    val mealDetail: LiveData<List<MealX>>
        get() = _mealDetail

    private val _searchMeal = MutableLiveData<List<Meal>>()
    val searchMeal: LiveData<List<Meal>>
        get() = _searchMeal


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

    suspend fun getMealById(mealId: String) {
        try {
            val result = api.retrofitService.getMealById(mealId)
            _mealDetail.postValue(result.meals)
        }catch(e: Exception) {
                Log.i("INFO", "schau im Repository nach bei getMealById : $e")
            }
    }


    suspend fun searchMeal(search: String) {
        try {
            val result = api.retrofitService.searchMeal(search)
            _searchMeal.postValue(result.meals)
        } catch (e: Exception) {
            Log.i("INFO", "schau im Repository nach bei getMealBySearch : $e")
        }
    }



}