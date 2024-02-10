package com.omrilhn.readerapp.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.omrilhn.readerapp.data.model.MUser
import javax.inject.Inject

class AuthRepository @Inject constructor(){
    //TODO: "NOT IMPLEMENTED YET", BusinessLogic will be seperated from LoginViewModel -> SignInWithMailPassword - CreateUser
    private val firestore = FirebaseFirestore.getInstance()
    private val auth:FirebaseAuth = Firebase.auth
    fun createUser(displayName:String?){
        val userId = auth.currentUser?.uid

        val user = com.omrilhn.readerapp.data.model.MUser(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is great",
            profession = "Android developer",
            id = null
        ).toMap()


        try {
            firestore.collection("users").add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("FIRESTORE", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("FIRESTORE", "Error adding document", e)
                }
        } catch (e: Exception) {
            Log.e("FIRESTORE", "Error adding document", e)
        }
    }


}
