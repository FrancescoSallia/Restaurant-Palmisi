package com.example.restaurantappprojektandroid.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.restaurantappprojektandroid.data.datasource.ReservationDatasource
import com.example.restaurantappprojektandroid.data.model.Meal
import com.example.restaurantappprojektandroid.data.model.Reservation
import com.example.restaurantappprojektandroid.data.remote.MealdbApi
import com.example.restaurantappprojektandroid.data.remote.Repository
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    //
    private val repository = Repository(MealdbApi, application)
    val ReservationDatasources = ReservationDatasource()

    val currentUser = repository.currentUser
    val likedMeals = repository.likedMeals
    val reservationsList = repository.reservationsList
    val reservation = repository.reservation
    val userData = repository.userData
    val userRef = repository.userRef

    private val _selectedPersonNumber = MutableLiveData<Int>(1)
    val selectedPersonNumber: LiveData<Int>
        get() = _selectedPersonNumber

    private val _selectedTime = MutableLiveData<String>()
    val selectedTime: LiveData<String>
        get() = _selectedTime

    private val _selectedMeal = MutableLiveData<Meal>()
    val selectedMeal: LiveData<Meal>
        get() = _selectedMeal

    fun uploadImage(uri: Uri) {
        repository.uploadImage(uri)
    }
    fun updateReservation(kommentarGast: String) {
        repository.updateReservation(kommentarGast)
    }
    fun snapShotListenerForReservation() {
        repository.snapShotListenerForReservation()
    }
    fun deleteReservation(reservationId: String) {
        repository.deleteReservation(reservationId)
    }
    fun getDataUser() {
        repository.getDataUser()
    }
    fun getReservations(reservationId: String) {
        repository.getDataReservation(reservationId)
    }
    fun postReservation(reservation: Reservation) {
        repository.postUserReservation(reservation)
    }
    fun selectedPersonNumber(anzahl: Int): Int {
        val wert = anzahl
        _selectedPersonNumber.postValue(wert)
        return wert
    }
    fun selectedTime(time: String): String {
        val zeit = time
        _selectedTime.postValue(zeit)
        return zeit
    }
    init {
        getMealsByCategory("Beef")
    }
    fun removeProfileImage(){
        repository.removeProfileImage()
    }
    fun resetPassword(email: String) {
        repository.resetPassword(email)
    }
    fun updateUser(vorname: String, nachname: String) {

        repository.updateUser(vorname, nachname)
    }
    fun deleteUser() {
        repository.deleteUser()
    }
    fun logIn(email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        repository.logIn(email, password, onSuccess, onFailure)
    }
    fun logOut() {
        repository.logOut()
    }
    fun registration(Email: String, password: String, vorname: String, nachname: String) {
        repository.registration(Email, password, vorname, nachname)
    }
    fun continueAsGuest() {
        repository.continueAsGuest()
    }
    //Firebase ENDE!!

    val repositoryCategory = repository.category
    val repositoryMeals = repository.meals
    val repositorySearchMeal = repository.searchMeal

    fun getCategories() {
        viewModelScope.launch {
            repository.getCategories()
        }
    }
    fun getMealsByCategory(categorieName: String) {
        viewModelScope.launch {
            repository.getLikedMeals()
            repository.getMealsByCategory(categorieName)
        }
    }
    fun searchMeal(mealName: String) {
        viewModelScope.launch {
            repository.searchMeal(mealName)
        }
    }
    fun setSelectedMealId(meal: Meal) {
        _selectedMeal.value = meal
    }
    fun addToFavorites(meal: Meal) {
        repository.addToFavorites(meal)
    }
}

