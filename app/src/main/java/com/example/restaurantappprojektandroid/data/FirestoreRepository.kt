package com.example.restaurantappprojektandroid.data

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
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
import com.google.firebase.storage.ktx.storage

class FirestoreRepository(val context: Context) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<FirebaseUser?>(auth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    private val _likedMeals = MutableLiveData<MutableList<Meal>>()
    val likedMeals: LiveData<MutableList<Meal>>
        get() = _likedMeals

    private val _reservationsList = MutableLiveData<MutableList<Reservation>>()
    val reservationsList: LiveData<MutableList<Reservation>>
        get() = _reservationsList

    private val _reservation = MutableLiveData<Reservation>()
    val reservation: LiveData<Reservation>
        get() = _reservation

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User>
        get() = _userData

    private val db = Firebase.firestore
    private val storage = Firebase.storage
    var storageRef = storage.reference
    var userRef : DocumentReference? = null
    var colRef: CollectionReference? = null
    var userCol : CollectionReference? = null
    var resRef: DocumentReference? = null


    fun resetPassword(email: String) {

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        context,
                        "Failed to send password reset email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun uploadImage(uri: Uri) {
        val imageRef = storageRef.child("images/${auth.currentUser!!.uid}//profilePic")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnCompleteListener {
            imageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    userRef?.update("profilePicture", it.result)
                }
            }
        }
    }

    fun updateReservation(kommentarGast: String) {
        resRef = db.collection("reservation").document(auth.currentUser?.uid ?: "")
            .collection("reservierung").document("${reservation.value?.reservationId}")
        resRef?.update(
            "kommentarGast", kommentarGast
        )
    }

    fun getDataUser(){
        userCol = db.collection("users")
        Log.i("FirestoreRepo", "-> ${auth.currentUser?.uid}")

        userCol?.document(auth.currentUser?.uid ?: "")?.get()?.addOnSuccessListener {
            Log.i("FirestoreRepo", "Daten wurden geladen $it")

            if (it != null && it.exists()) {
                val user = it.toObject(User::class.java)
                Log.e("USER", user.toString())
                _userData.value = user!!
            }

        }?.addOnFailureListener {
            Toast.makeText(context, "Fehler beim Laden der Daten vom User", Toast.LENGTH_SHORT).show()
        }
    }

    fun postUserReservation(reservation:Reservation){
        Log.i("FirestoreRepo", "Reservation wird gespeichert: -> $reservation")
        colRef = db.collection("reservation").document(auth.currentUser!!.uid).collection("reservierung")
        colRef?.add(reservation)
            ?.addOnSuccessListener {
                Toast.makeText(context, "Reservation erfolgreich gespeichert", Toast.LENGTH_SHORT)
                    .show()
            }
            ?.addOnFailureListener {
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
            getLikedMeals()
            snapShotListenerForReservation()
        }
    }

     fun getLikedMeals() {
       try {
           userRef = db.collection("users").document(auth.currentUser!!.uid)
           userRef?.addSnapshotListener { value, error ->
               if (error == null && value != null && value.exists()) {
                   val user = value.toObject(User::class.java)
                   if (user != null) {
                       _likedMeals.postValue(user.likedGerichte)
                   }
               }
           }
       } catch (e: Exception) {
           Log.e("FirestoreRepository", "Error fetching liked meals: ${e.message}")
       }
    }

    fun getDataReservation(reservierungsId: String){
        db.collection("reservation").document(auth.currentUser!!.uid).collection("reservierung").document(reservierungsId)
            .get().addOnSuccessListener {
                val reservation = it.toObject(Reservation::class.java)
                if (reservation != null){
                    _reservation.postValue(reservation!!)
                }
            }

    }
     fun snapShotListenerForReservation(){
        colRef = db.collection("reservation").document(auth.currentUser!!.uid).collection("reservierung")
        colRef?.addSnapshotListener { value, error ->
            if (error == null && value != null) {

                val newReservation = value.toObjects(Reservation::class.java)
                _reservationsList.postValue(newReservation)
            }
        }

    }

    // ein User erstellen
    private fun createUser(vorname: String, nachname: String): User {
        return User(vorname = vorname, nachname = nachname)
    }

    fun updateUser(vorname: String, nachname: String) {
        if (auth.currentUser?.uid != null) {
            userRef?.update(
                "vorname",
                vorname,
                "nachname" ,
                nachname
            )?.addOnSuccessListener {
                Log.d("Firestore", "Benutzerdaten erfolgreich aktualisiert")
                Toast.makeText(context, "Benutzerdaten aktualisiert", Toast.LENGTH_SHORT)
                    .show()
            }?.addOnFailureListener { e ->
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

    fun deleteReservation(reservationId: String){
        val userId = auth.currentUser?.uid
        if (userId != null) {
            userRef = db.collection("reservation").document(userId).collection("reservierung").document(reservationId)
            userRef?.delete()?.addOnSuccessListener {
                Log.d("Firestore", "userRef: $userRef")
            }
        }
    }

    fun deleteUser() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            userRef = db.collection("users").document(userId)
            userRef?.delete()?.addOnSuccessListener {
                Log.d("Firestore", "userRef: $userRef")

                // Die untere Zeile, löscht alle die Reservierungen vom User!
                userRef = db.collection("reservation").document(userId)
                userRef?.delete()?.addOnSuccessListener {Log.d("Firestore", "Reservierungen vom user wurden auch gelöscht!")}
                auth.currentUser?.delete()
                    ?.addOnSuccessListener {
                        Toast.makeText(context, "Benutzer gelöscht", Toast.LENGTH_SHORT)
                            .show()
                        logOut()
                    }
                    ?.addOnFailureListener { error ->

                        Log.d("Firestore", "Fehler beim Löschen des Benutzers: $error")

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
        userRef?.set(user)?.addOnCompleteListener { task ->
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

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _currentUser.postValue(it.result.user)
                    getLikedMeals()
                    onSuccess()
                } else {
                    onFailure()
                }
            }
    }

    fun logOut() {
        //falls der user anonym ist, dann wird der user gelöscht beim ausloggen
        if (auth.currentUser?.isAnonymous == true) {
            auth.currentUser?.delete()
        }
        _currentUser.value = null
         userRef  = null
         colRef = null
         userCol = null
         resRef = null

        auth.signOut()
    }

    fun registration(Email: String, password: String, vorname: String, nachname: String) {

        auth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener { newUser ->
            if (newUser.isSuccessful) {

                _currentUser.value = newUser.result.user
                getLikedMeals()
                postDokument(createUser(vorname, nachname))

            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun continueAsGuest() {
        auth.signInAnonymously().addOnSuccessListener {
            _currentUser.postValue(it.user)
        }
    }

    fun addToFavorites(meal: Meal) {
        Log.d("Firestore", "addToFavorites: $meal")
        try {
                if (!likedMeals.value!!.contains(meal) && meal.liked) {
                    likedMeals.value?.add(meal)
                    updateMealFromFirestore()
                    Log.d("Firestore", "likedMeal.value: ${likedMeals.value}")
                } else {
                    likedMeals.value?.removeIf { it.idMeal == meal.idMeal }
                    updateMealFromFirestore()
                }

        }catch (e: Exception) {
            Log.d("Firestore", "error : $e")
        }

    }

    private fun updateMealFromFirestore() {

        var newMap = mutableListOf<Map<String, Any>>()

        likedMeals.value?.forEach {
            Log.d("Firestore", "meal: $it")

            newMap.add(it.toMap())
        }
        var upToDate = mapOf(
            "likedGerichte" to newMap,
        )
        db.collection("users").document(auth.currentUser!!.uid).update(upToDate)

    }



}