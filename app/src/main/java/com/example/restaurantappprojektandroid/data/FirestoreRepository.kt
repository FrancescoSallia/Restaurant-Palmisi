package com.example.restaurantappprojektandroid.data

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantappprojektandroid.data.model.Meal
import com.example.restaurantappprojektandroid.data.model.Reservation
import com.example.restaurantappprojektandroid.data.model.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay

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

    //private val storage = Firebase.storage
    // var storageRef = storage.reference
    var userRef: DocumentReference? = null
    var colRef: CollectionReference? = null
    var userCol: CollectionReference? = null
    var resRef: DocumentReference? = null


    fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Email wurde versendet", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        context,
                        "Email konnte nicht versendet werden",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

//    fun uploadImage(uri: Uri) {
//        val imageRef = storageRef.child("images/${auth.currentUser!!.uid}//profilePic")
//        val uploadTask = imageRef.putFile(uri)
//        uploadTask.addOnCompleteListener {
//            imageRef.downloadUrl.addOnCompleteListener {
//                if (it.isSuccessful) {
//                    userRef?.update("profilePicture", it.result)
//                }
//            }
//        }
//    }

//    fun removeProfileImage() {
//        val user = auth.currentUser
//        if (user != null) {
//            val imageRef = storageRef.child("images/${user.uid}/profilePic")
//
//            // Löschen des Bildes aus dem Storage
//            imageRef.delete().addOnCompleteListener { deleteTask ->
//                if (deleteTask.isSuccessful) {
//                    // Wenn das Bild erfolgreich gelöscht wurde, aktualisieren Sie die Benutzerdaten
//                    userRef?.update("profilePicture", null)
//                        ?.addOnSuccessListener {
//                            // Erfolgreiche Aktualisierung der Benutzerdaten
//                            Toast.makeText(
//                                context,
//                                "Profilbild erfolgreich entfernt",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        ?.addOnFailureListener { e ->
//                            // Fehler bei der Aktualisierung der Benutzerdaten
//                            Toast.makeText(
//                                context,
//                                "Fehler beim Entfernen des Profilbildes: ${e.message}",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                } else {
//                    // Fehler beim Löschen des Bildes
//                    println("Fehler beim Löschen des Profilbildes: ${deleteTask.exception?.message}")
//                }
//            }
//        } else {
//            println("Kein Benutzer angemeldet")
//        }
//    }


//    fun updateReservation(kommentarGast: String) {
//        resRef = db.collection("reservation").document(auth.currentUser?.uid ?: "")
//            .collection("reservierung").document("${reservation.value?.reservationId}")
//        resRef?.update(
//            "kommentarGast", kommentarGast
//        )
//    }

    fun updateReservation(kommentarGast: String) {
        val userId = auth.currentUser?.uid ?: return
        val resId = reservation.value?.reservationId ?: return

        val resRef = db.collection("reservation").document(userId)
            .collection("reservierung").document(resId)

        resRef.get().addOnSuccessListener { doc ->
            if (doc.exists()) {
                resRef.update("kommentarGast", kommentarGast)
                    .addOnFailureListener {
                        Log.e("error", "Fehler beim Update: $it")
                    }
            } else {
                Log.w("error", "Reservierung nicht gefunden – ID war vermutlich veraltet: $resId")
            }
        }.addOnFailureListener {
            Log.e("error", "Fehler beim Zugriff auf die Reservierung: $it")
        }
    }


    fun getDataUser() {
        userCol = db.collection("users")

        userCol?.document(auth.currentUser?.uid ?: "")?.get()?.addOnSuccessListener {

            if (it != null && it.exists()) {
                val user = it.toObject(User::class.java)
                _userData.value = user!!
            }

        }?.addOnFailureListener {
            Toast.makeText(context, "Fehler beim Laden deiner Daten", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun postUserReservation(reservation: Reservation) {
        colRef =
            db.collection("reservation").document(auth.currentUser!!.uid).collection("reservierung")
        colRef?.add(reservation)
            ?.addOnSuccessListener {
                Toast.makeText(context, "Reservierung Erfolgreich", Toast.LENGTH_SHORT)
                    .show()
            }
            ?.addOnFailureListener {
                Toast.makeText(
                    context,
                    "Fehler beim Reservieren",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }


    //Firebase START!!

    init {
        if (auth.currentUser != null) {
            userRef = db.collection("users").document(auth.currentUser!!.uid)
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
            Log.e("error", "Error getLikedMeals: $e")
        }
    }

    fun getDataReservation(reservierungsId: String) {
        db.collection("reservation").document(auth.currentUser!!.uid).collection("reservierung")
            .document(reservierungsId)
            .get().addOnSuccessListener {
                val reservation = it.toObject(Reservation::class.java)
                if (reservation != null) {
                    _reservation.postValue(reservation!!)
                }
            }
    }

    fun snapShotListenerForReservation() {
        colRef =
            db.collection("reservation").document(auth.currentUser!!.uid).collection("reservierung")
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

    fun updateUser(vorname: String, nachname: String, profilPicture: Uri? = null) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userRef = db.collection("users")
                .document(userId) // <- neu holen die referenz damit man die frischen daten hat nach dem löschen von einer reservierung zum beisoiel.
//        if (auth.currentUser?.uid != null) {
            userRef.update(
                "vorname",
                vorname,
                "nachname",
                nachname,
                "profilePicture",
                profilPicture
            ).addOnSuccessListener {
                Toast.makeText(context, "Deine Daten wurden aktualisiert", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener { e ->
                Log.e("error", "Error update data: $e")

                Toast.makeText(
                    context,
                    "Fehler beim Aktualisieren der Daten",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(context, "Kein angemeldeter Benutzer", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun deleteReservation(reservationId: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            userRef = db.collection("reservation").document(userId).collection("reservierung")
                .document(reservationId)
            userRef?.delete()?.addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Reservierung erfolgreich storniert", Toast.LENGTH_SHORT
                ).show()
            }?.addOnFailureListener { e ->
                Log.e("error", "Error deleting Reservation: $e")

                Toast.makeText(
                    context,
                    "Reservation konnte nicht storniert werden",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

//    private fun deleteAllReservationsForCurrentUser(userId: String) {
//        db.collection("reservation").document(userId).collection("reservierung")
//            .get().addOnSuccessListener { snapshot ->
//                snapshot.documents.forEach {
//                    it.reference.delete().addOnSuccessListener {
//                        Toast.makeText(
//                            context,
//                            "Alle Reservierungen wurden erfolgreich gelöscht",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }.addOnSuccessListener { success ->
//                        Log.i("success", "succesDeleteAllReservationForCurrentUser: $success")
//                    }.addOnFailureListener { e ->
//                        Log.e("error", "Fehler beim Löschen der Reservierungen: $e")
//                    }
//                }
//            }
//    }

    fun deleteUser(onComplete: () -> Unit) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Toast.makeText(context, "Kein angemeldeter Benutzer", Toast.LENGTH_SHORT).show()
            return
        }

        deleteAllReservationsForCurrentUser(userId) {
            deleteProfileRefForCurrentUser(userId) {
                deleteFirebaseCurrentUser {
                    logOut {
                        onComplete()
                    }
                }
            }
        }
    }






    private fun deleteAllReservationsForCurrentUser(userId: String, onComplete: () -> Unit) {
        db.collection("reservation").document(userId).collection("reservierung")
            .get().addOnSuccessListener { snapshot ->
                val docs = snapshot.documents
                if (docs.isEmpty()) {
                    onComplete()
                    return@addOnSuccessListener
                }

                var deletedCount = 0
                for (doc in docs) {
                    doc.reference.delete().addOnSuccessListener {
                        deletedCount++
                        if (deletedCount == docs.size) {
                            onComplete()
                        }
                    }.addOnFailureListener {
                        Log.e("error", "Fehler beim Löschen: $it")
                        deletedCount++
                        if (deletedCount == docs.size) {
                            onComplete()
                        }
                    }
                }
            }.addOnFailureListener {
                Log.e("error", "Fehler beim Lesen der Reservierungen: $it")
                onComplete()
            }
    }

    private fun deleteProfileRefForCurrentUser(userId: String, onComplete: () -> Unit) {
        val ref = db.collection("users").document(userId)
        ref.delete().addOnSuccessListener {
            Log.d("Firestore", "User-Dokument gelöscht")
            onComplete()
        }.addOnFailureListener {
            Log.e("Firestore", "Fehler beim Löschen: $it")
            onComplete()
        }
    }

    private fun deleteFirebaseCurrentUser(onComplete: () -> Unit) {
        auth.currentUser?.delete()
            ?.addOnSuccessListener {
                Toast.makeText(context, "Account gelöscht", Toast.LENGTH_SHORT).show()
                onComplete()
            }
            ?.addOnFailureListener { error ->
                Log.e("error", "Fehler beim Löschen des Accounts: $error")
                Toast.makeText(context, "Fehler beim Löschen des Accounts", Toast.LENGTH_SHORT).show()
                onComplete()
            }
    }







//    private fun deleteProfileStoragePicture(userId: String?) {
//        val imageRef = storageRef.child("images/${userId}/profilePic")
//        imageRef.delete()
//    }

//    private fun deleteProfileRefForCurrentUser(userId: String) {
//        userRef = db.collection("users").document(userId)
//        userRef?.delete()?.addOnSuccessListener {
//            Log.d("Firestore", "userRef: $userRef")
//        }
//    }
//
//    private fun deleteFirebaseCurrentUser() {
//        auth.currentUser?.delete()
//            ?.addOnSuccessListener {
//                Toast.makeText(context, "Account gelöscht", Toast.LENGTH_SHORT)
//                    .show()
//            }
//            ?.addOnFailureListener { error ->
//                Log.e("error", "Error deleting user: $error")
//                Toast.makeText(
//                    context,
//                    "Fehler beim Löschen des Accounts",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//    }

    //ein User speichern in die datenbank
    private fun postDokument(user: User) {
        userRef?.set(user)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Erfolgreich Registriert", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Fehler beim Registrieren", Toast.LENGTH_SHORT).show()
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


//    fun reAuthentification(email: String, password: String, success: () -> Unit, onFailure: (Exception) -> Unit) {
//        val credential = EmailAuthProvider.getCredential(email, password)
//        auth.currentUser?.reauthenticate(credential)
//            ?.addOnSuccessListener {
//                Log.i("success", "Erfolgreich Re-Authentifiziert")
//                deleteUser()
//                logOut()
//                success
//            }
//            ?.addOnFailureListener { error ->
//                Toast.makeText(context, "Passwort ist falsch oder ungültig", Toast.LENGTH_SHORT).show()
//                Log.e("error", "Fehler bei Re-Authentifizierung: $error")
//                onFailure
//            }
//    }


    fun reAuthentification(
        email: String,
        password: String,
        success: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val credential = EmailAuthProvider.getCredential(email, password)
        val user = auth.currentUser

        if (user == null) {
            onFailure(Exception("Kein angemeldeter Benutzer"))
            return
        }

        user.reauthenticate(credential)
            .addOnSuccessListener {
                Log.i("success", "Erfolgreich Re-Authentifiziert")
//                deleteUser()  // 🔒 jetzt sicher!
//                logOut()
                success()      // ✅ korrekt aufrufen
            }
            .addOnFailureListener { error ->
                Toast.makeText(context, "Passwort ist falsch oder ungültig", Toast.LENGTH_SHORT).show()
                Log.e("error", "Fehler bei Re-Authentifizierung: $error")
                onFailure(error)  // ✅ korrekt aufrufen
            }
    }



    fun logOut(onComplete: () -> Unit) {
        //falls der user anonym ist, dann wird der user gelöscht beim ausloggen
        if (auth.currentUser?.isAnonymous == true) {
            auth.currentUser?.delete()
        }
        _currentUser.value = null
        userRef = null
        colRef = null
        userCol = null
        resRef = null

        auth.signOut()
        onComplete()
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
        try {
            if (!likedMeals.value!!.contains(meal) && meal.liked) {
                likedMeals.value?.add(meal)
                updateMealFromFirestore()
                Log.d("Firestore", "likedMeal.value: ${likedMeals.value}")
            } else {
                likedMeals.value?.removeIf { it.idMeal == meal.idMeal }
                updateMealFromFirestore()
            }
        } catch (e: Exception) {
            Log.e("Firestore", "error : $e")
        }
    }

    private fun updateMealFromFirestore() {
        var newMap = mutableListOf<Map<String, Any>>()
        likedMeals.value?.forEach {
            newMap.add(it.toMap())
        }
        var upToDate = mapOf(
            "likedGerichte" to newMap,
        )
        db.collection("users").document(auth.currentUser!!.uid).update(upToDate)
    }
}