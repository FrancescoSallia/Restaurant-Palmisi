package com.example.restaurantappprojektandroid.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantappprojektandroid.data.FirestoreRepository
import com.example.restaurantappprojektandroid.data.datasource.ReservationDatasource
import com.example.restaurantappprojektandroid.data.model.Meal
import com.example.restaurantappprojektandroid.data.remote.MealdbApi
import com.example.restaurantappprojektandroid.data.remote.Repository
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


    fun logIn(email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        firestore.logIn(email, password, onSuccess, onFailure)
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

