package com.example.restaurantappprojektandroid.ui

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
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
import java.io.File
import java.io.FileOutputStream


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

//    fun uploadImage(uri: Uri) {
//        repository.uploadImage(uri)
//    }
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
//    fun removeProfileImage(){
//        repository.removeProfileImage()
//    }
    fun resetPassword(email: String) {
        repository.resetPassword(email)
    }
    fun updateUser(vorname: String, nachname: String, profilPicture: Uri? = null) {

        repository.updateUser(vorname, nachname, profilPicture)
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

    // Funktion zum Speichern des Bildes lokal
    fun saveImageLocally(context: Context, imageUri: Uri) {
        try {
            val file = File(context.filesDir, "profilbild.jpg")
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val outputStream = FileOutputStream(file)

            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()

            Log.d("Image Save", "Image saved successfully")
        } catch (e: Exception) {
            Log.e("Image Save Error", "Error saving image: ${e.message}")
        }
    }
    // Funktion zum Entfernen des Bildes lokal
    fun removeLocalImage(context: Context) {
        val file = File(context.filesDir, "profilbild.jpg")
        if (file.exists()) {
            val deleted = file.delete()
            if (deleted) {
                Log.d("Image Removal", "Profile image deleted successfully")
            } else {
                Log.d("Image Removal", "Failed to delete profile image")
            }
        } else {
            Log.d("Image Removal", "No profile image found to delete")
        }
    }

    // Funktion zum Laden des Bildes
    fun loadLocalImage(context: Context): Uri? {
        val file = File(context.filesDir, "profilbild.jpg")
        return if (file.exists()) {
            Uri.fromFile(file)
        } else {
            null
        }
    }
}

