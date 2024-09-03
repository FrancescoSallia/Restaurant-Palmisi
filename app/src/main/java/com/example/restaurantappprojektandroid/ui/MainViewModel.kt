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

    fun getCategories() {
        viewModelScope.launch {
            Log.i("TAG", repositoryCategory.value.toString())

            repository.category
        }

    }
}