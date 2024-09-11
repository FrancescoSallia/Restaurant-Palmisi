package com.example.restaurantappprojektandroid.ui

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.restaurantappprojektandroid.datasource.ReservationDatasource
import com.example.restaurantappprojektandroid.model.Meal
import com.example.restaurantappprojektandroid.model.Reservation
import com.example.restaurantappprojektandroid.model.User
import com.example.restaurantappprojektandroid.remote.MealdbApi
import com.example.restaurantappprojektandroid.remote.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    //
    private val repository = Repository(MealdbApi)
    val ReservationDatasources = ReservationDatasource()

    private var firestore = FirestoreRepository(getApplication())
    val userRef = firestore.userRef

    val currentUser = firestore.currentUser

    val likedMeals = firestore.likedMeals

    val reservations = firestore.reservations


    init {
        getMealsByCategory("Beef")
    }

    fun updateUser(vorname: String, nachname: String) {

        firestore.updateUser(vorname, nachname)
    }

    fun deleteUser() {
        firestore.deleteUser()
    }


    fun logIn(email: String, password: String) {
        firestore.logIn(email, password)
    }


    fun logOut() {
        firestore.logOut()
    }

    fun registration(Email: String, password: String, vorname: String, nachname: String) {
        firestore.registration(Email, password, vorname, nachname)
    }

    fun continueAsGuest() {
        firestore.continueAsGuest()
    }
    //Firebase ENDE!!

    var selectedMealID = ""
    var recyclerViewPosition = 0


    val repositoryCategory = repository.category
    val repositoryMeals = repository.meals
    val repositoryMealDetail = repository.mealDetail
    val repositorySearchMeal = repository.searchMeal

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

    fun getMealById() {
        viewModelScope.launch {
            repository.getMealById(selectedMealID)
        }
    }

    fun searchMeal(mealName: String) {
        viewModelScope.launch {
            repository.searchMeal(mealName)
        }
    }

    fun setSelectedMealId(mealId: String) {
        selectedMealID = mealId
    }


    fun addToFavorites(meal: Meal) {
        firestore.addToFavorites(meal)
    }

    fun removeFromFavorites(meal: Meal) {
        firestore.removeFromFavorites(meal)
    }


}

