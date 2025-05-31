package com.example.restaurantappprojektandroid.data.remote

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantappprojektandroid.data.FirestoreRepository
import com.example.restaurantappprojektandroid.data.model.Category
import com.example.restaurantappprojektandroid.data.model.Meal
import com.example.restaurantappprojektandroid.data.model.Reservation

class Repository(private val api: MealdbApi, context: Context) {

    //region Firebase
    private val _categorie = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>>
        get() = _categorie

    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>>
        get() = _meals

    private val _searchMeal = MutableLiveData<List<Meal>>()
    val searchMeal: LiveData<List<Meal>>
        get() = _searchMeal


    private val firestore = FirestoreRepository(context)

    val currentUser = firestore.currentUser

    val likedMeals = firestore.likedMeals

    val reservationsList = firestore.reservationsList

    val reservation = firestore.reservation

    val userData = firestore.userData


    val userRef = firestore.userRef

    //endregion

    //region Firebase functions
//    fun removeProfileImage() {
//        firestore.removeProfileImage()
//    }

    fun resetPassword(email: String) {
        firestore.resetPassword(email)
    }

//    fun uploadImage(uri: Uri) {
//        firestore.uploadImage(uri)
//    }

    fun updateReservation(kommentarGast: String) {
        firestore.updateReservation(kommentarGast)
    }

    fun getLikedMeals() {
        firestore.getLikedMeals()
    }

    fun snapShotListenerForReservation() {
        firestore.snapShotListenerForReservation()
    }

    fun deleteReservation(reservationId: String) {
        firestore.deleteReservation(reservationId)
    }

    fun getDataUser() {
        firestore.getDataUser()
    }

    fun getDataReservation(reservationId: String) {
        firestore.getDataReservation(reservationId)
    }

    fun postUserReservation(reservation: Reservation) {
        firestore.postUserReservation(reservation)
    }

    fun updateUser(vorname: String, nachname: String, profilPicture : Uri? = null) {
        firestore.updateUser(vorname, nachname, profilPicture)
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

    fun addToFavorites(meal: Meal) {
        firestore.addToFavorites(meal)
    }


    //endregion


    //region Api and Retofit functions
    suspend fun getCategories() {
        try {
            val result = MealdbApi.retrofitService.getCategories()
            _categorie.postValue(result.categories)
        } catch (e: Exception) {
            Log.i("INFO", "schau im Repository nach bei getCategories")
        }
    }

    suspend fun getMealsByCategory(categorieName: String) {
        try {
            val result = MealdbApi.retrofitService.getMealsByCategory(categorieName)
            result.meals.forEach { meal ->
                var likedMeal = likedMeals.value?.find { it.idMeal == meal.idMeal }
                if (likedMeal != null) {
                    meal.liked = likedMeal.liked
                }
            }
            _meals.postValue(result.meals)

        } catch (e: Exception) {
            Log.i("INFO", "schau im Repository nach bei getMealsByCategory : $e")
        }
    }

    suspend fun searchMeal(search: String) {
        try {
            val result = MealdbApi.retrofitService.searchMeal(search)
            result.meals.forEach { meal ->
                var likedMeal = likedMeals.value?.find { it.idMeal == meal.idMeal }
                if (likedMeal != null) {
                    meal.liked = likedMeal.liked
                }
            }
            _meals.postValue(result.meals)
        } catch (e: Exception) {
            Log.i("INFO", "schau im Repository nach bei getMealBySearch : $e")
        }
    }


    //endregion


}