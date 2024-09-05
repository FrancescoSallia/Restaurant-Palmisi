package com.example.restaurantappprojektandroid.ui

import android.app.Application
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.restaurantappprojektandroid.remote.MealdbApi
import com.example.restaurantappprojektandroid.remote.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch



class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(MealdbApi)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    //Firebase logik START!!

    private val _logInResult = MutableLiveData<Boolean>()
    val logInResult: LiveData<Boolean>
    get() = _logInResult

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser



    fun logIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _logInResult.postValue(true)
                    _currentUser.postValue(auth.currentUser)
                }else{
                    _logInResult.postValue(false)
                    Toast.makeText(getApplication(),
                        "Deine angegebenen daten sind falsch, oder du musst dich Registrieren.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun logOut() {
        auth.signOut()
        _currentUser.postValue(null)
    }

    fun registration(Email: String, password: String){

        auth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener { newUser ->
            if (newUser.isSuccessful) {

                _currentUser.postValue(auth.currentUser)

            }else{
                Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun continueAsGuest() {
        auth.signInAnonymously()
        _currentUser.postValue(auth.currentUser)
    }

    //Firebase logik ENDE!!

    var selectedMealID = ""
       var heartFilledout = false
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

    //um die herzen zu ändern , zwischen die fragmente auch wie im MealDetail zb
    fun setHeartFilled(filled: Boolean) {
        heartFilledout = filled
        heartFilledout != heartFilledout

    }


}

