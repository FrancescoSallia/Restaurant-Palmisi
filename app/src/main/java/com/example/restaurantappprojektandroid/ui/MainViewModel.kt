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
import com.example.restaurantappprojektandroid.model.Meal
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


    private val repository = Repository(MealdbApi)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()



    private val _currentUser = MutableLiveData<FirebaseUser?>(auth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser


    private val _likedMeals = MutableLiveData<MutableList<Meal>>()
    val likedMeals: LiveData<MutableList<Meal>>
        get() = _likedMeals


    //Firebase datenbank (cloud)
    val db = Firebase.firestore
    lateinit var userRef: DocumentReference


    //Firebase START!!

    init {
        getMealsByCategory("Beef")

        if (auth.currentUser != null) {
            Log.d("DEBUG", "User is logged in: ${auth.currentUser?.uid}")
            addSnapshotListenerForCurrentUser()
        }
    }

    private fun addSnapshotListenerForCurrentUser() {
        userRef = db.collection("users").document(auth.currentUser!!.uid)
        userRef.addSnapshotListener { value, error ->
            if (error == null && value != null && value.exists()) {
                val user = value.toObject(User::class.java)
                if (user != null) {
                    _likedMeals.postValue(user.likedGerichte)
                }
            }
        }
    }

    // ein User erstellen
    private fun createUser(vorname: String, nachname: String): User {
        return User(vorname = vorname, nachname = nachname)
    }

    fun updateUser(vorname: String, nachname: String) {
        if (auth.currentUser?.uid != null) {
            userRef.update(
                "vorname", vorname,
                "nachname", nachname
            ).addOnSuccessListener {
                Log.d("Firestore", "Benutzerdaten erfolgreich aktualisiert")
                Toast.makeText(getApplication(), "Benutzerdaten aktualisiert", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener { e ->
                Log.w("Firestore", "Fehler beim Aktualisieren der Benutzerdaten", e)
                Toast.makeText(
                    getApplication(),
                    "Fehler beim Aktualisieren der Daten",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Log.w("Firestore", "Kein angemeldeter Benutzer gefunden")
            Toast.makeText(getApplication(), "Kein angemeldeter Benutzer", Toast.LENGTH_SHORT)
                .show()
        }
    }


    fun deleteUser() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            userRef = db.collection("users").document(userId)
            userRef.delete().addOnSuccessListener {
                Log.d("Firestore", "userRef: $userRef")
                auth.currentUser?.delete()
                    ?.addOnSuccessListener {
                        Toast.makeText(getApplication(), "Benutzer gelöscht", Toast.LENGTH_SHORT)
                            .show()
                        logOut()
                    }
                    ?.addOnFailureListener {

                        Toast.makeText(
                            getApplication(),
                            "Fehler beim Löschen des Benutzers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        } else {
            Toast.makeText(getApplication(), "Kein angemeldeter Benutzer", Toast.LENGTH_SHORT)
                .show()
        }
    }

    //ein User speichern in die datenbank
    fun postDokument(user: User) {
        userRef.set(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firestore", "Dokument erstellt -> ID : ${task.result}")
                Toast.makeText(getApplication(), "Dokument erstellt", Toast.LENGTH_SHORT).show()
            } else {
                Log.d(
                    "Firestore",
                    "Dokument nicht erstellt, schau in ViewModel -> ID : ${task.result}"
                )

                Toast.makeText(getApplication(), "Fehler beim erstellen", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun logIn(email: String, password: String) {
        Log.d("DEBUG", "logInFunktion: ${auth.currentUser?.uid}")

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    _currentUser.postValue(it.result.user)
                    addSnapshotListenerForCurrentUser()
                } else {
                    Toast.makeText(
                        getApplication(),
                        "Deine angegebenen daten sind falsch, oder du musst dich Registrieren.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun logOut() {
        auth.signOut()
        _currentUser.postValue(null)
    }

    fun registration(Email: String, password: String, vorname: String, nachname: String) {

        auth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener { newUser ->
            if (newUser.isSuccessful) {

                _currentUser.value = newUser.result.user
                addSnapshotListenerForCurrentUser()
                postDokument(createUser(vorname, nachname))

            } else {
                Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun continueAsGuest() {
        auth.signInAnonymously()
        _currentUser.postValue(auth.currentUser)
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

        if (!likedMeals.value!!.contains(meal)) {
            likedMeals.value?.add(meal)
            updateMealFromFirestore()
        }
    }


    fun removeFromFavorites(meal: Meal) {

        likedMeals.value?.remove(meal)
        updateMealFromFirestore()
    }


    private fun updateMealFromFirestore() {

        var newMap = mutableListOf<Map<String, Any>>()

        likedMeals.value?.forEach {
            newMap.add(it.toMap())
        }
        var upToDate = mapOf(
            "likedGerichte" to newMap,
        )
        db.collection("users").document(auth.currentUser!!.uid).set(upToDate)

    }

}

