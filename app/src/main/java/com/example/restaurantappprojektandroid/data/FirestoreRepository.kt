package com.example.restaurantappprojektandroid.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantappprojektandroid.data.model.Meal
import com.example.restaurantappprojektandroid.data.model.Reservation
import com.example.restaurantappprojektandroid.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreRepository(val context: Context) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<FirebaseUser?>(auth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    private val _likedMeals = MutableLiveData<MutableList<Meal>>()
    val likedMeals: LiveData<MutableList<Meal>>
        get() = _likedMeals

    private val _reservations = MutableLiveData<MutableList<Reservation>>()
    val reservations: LiveData<MutableList<Reservation>>
        get() = _reservations

    private val db = Firebase.firestore
    lateinit var userRef: DocumentReference
    lateinit var colRef: CollectionReference



    fun postUserReservation(reservation:Reservation){
        colRef = db.collection("reservation").document(auth.currentUser!!.uid).collection("reservierung")
        colRef.add(reservation.toMap())
            .addOnSuccessListener {
                Toast.makeText(context, "Reservation erfolgreich gespeichert", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Reservation konnte nicht gespeichert werden",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }


    //Firebase START!!

    init {
        if (auth.currentUser != null) {
            Log.d("DEBUG", "User is logged in: ${auth.currentUser?.uid}")
            addSnapshotListenerForCurrentUser()
            snapShotListenerForReservation()
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

    private fun snapShotListenerForReservation(){
        colRef = db.collection("reservation").document(auth.currentUser!!.uid).collection("reservierung")
        colRef.addSnapshotListener { value, error ->
            if (error == null && value != null) {
                val tempList = mutableListOf<Reservation>()
                for (dokument in value.documents){
                    val reservation = dokument.data?.let { Reservation(it.toMap()) }
                    if (reservation != null) {
                        tempList.add(reservation)
                    }
                }
                _reservations.postValue(tempList)

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
                Toast.makeText(context, "Benutzerdaten aktualisiert", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener { e ->
                Log.w("Firestore", "Fehler beim Aktualisieren der Benutzerdaten", e)
                Toast.makeText(
                    context,
                    "Fehler beim Aktualisieren der Daten",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Log.w("Firestore", "Kein angemeldeter Benutzer gefunden")
            Toast.makeText(context, "Kein angemeldeter Benutzer", Toast.LENGTH_SHORT)
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
                        Toast.makeText(context, "Benutzer gelöscht", Toast.LENGTH_SHORT)
                            .show()
                        logOut()
                    }
                    ?.addOnFailureListener {

                        Toast.makeText(
                            context,
                            "Fehler beim Löschen des Benutzers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        } else {
            Toast.makeText(context, "Kein angemeldeter Benutzer", Toast.LENGTH_SHORT)
                .show()
        }
    }

    //ein User speichern in die datenbank
   private fun postDokument(user: User) {
        userRef.set(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firestore", "Dokument erstellt -> ID : ${task.result}")
                Toast.makeText(context, "Dokument erstellt", Toast.LENGTH_SHORT).show()
            } else {
                Log.d(
                    "Firestore",
                    "Dokument nicht erstellt, schau in ViewModel -> ID : ${task.result}"
                )

                Toast.makeText(context, "Fehler beim erstellen", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun logIn(email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        Log.d("DEBUG", "logInFunktion: ${auth.currentUser?.uid}")

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _currentUser.postValue(it.result.user)
                    addSnapshotListenerForCurrentUser()
                    onSuccess()
                } else {
                    onFailure()
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
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun continueAsGuest() {
        auth.signInAnonymously()
        _currentUser.postValue(auth.currentUser)
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