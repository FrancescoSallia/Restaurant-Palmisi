package com.example.restaurantappprojektandroid.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantappprojektandroid.model.Category

class Repository(private val api: MealdbApi) {


    private val _categorie = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>>
        get() = _categorie


    suspend fun getCategories(){
        Log.i("TAG", category.value.toString())

        try {
            val result = api.retrofitService.getCategories()
            _categorie.postValue(result)
        } catch (e: Exception) {
            Log.i("INFO","schau im Repository nach bei getCategories")
        }

    }



}