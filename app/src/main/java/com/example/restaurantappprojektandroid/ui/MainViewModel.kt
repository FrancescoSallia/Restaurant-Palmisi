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

    val profilPicture = repository.profilPicture

    private val _selectedPersonNumber = MutableLiveData<Int>(1)
    val selectedPersonNumber: LiveData<Int>
        get() = _selectedPersonNumber

    private val _selectedTime = MutableLiveData<String>()
    val selectedTime: LiveData<String>
        get() = _selectedTime


//    fun updateProfilPicture(profilBild: Uri) {
//        repository.updateProfilPicture(profilBild)
//    }

    fun addProfilPicture(uri:Uri){
        repository.addProfilPicture(uri)
    }

    fun updateReservation(kommentarGast: String){
        repository.updateReservation(kommentarGast)
    }
    fun addSnapshotListenerForCurrentUser(){
        repository.getLikedMeals()
    }

    fun snapShotListenerForReservation(){
        repository.snapShotListenerForReservation()
    }
    fun deleteReservation(reservationId:String){
        repository.deleteReservation(reservationId)
    }
    fun getDataUser(){
        repository.getDataUser()

    }

    fun getReservations(reservationId: String){
        repository.getDataReservation(reservationId)
    }


    fun postReservation(reservation: Reservation) {
        repository.postUserReservation(reservation)
    }

    fun selectedPersonNumber(anzahl:Int):Int{

        val wert = anzahl
        _selectedPersonNumber.postValue(wert)
        return wert
    }

    fun selectedTime(time:String):String{

        val zeit = time
        _selectedTime.postValue(zeit)
        return zeit
    }



    init {
        getMealsByCategory("Beef")
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

    var selectedMealID = ""


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

            repository.getLikedMeals()
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
        repository.addToFavorites(meal)
    }




}

