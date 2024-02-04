package com.omrilhn.readerapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.omrilhn.readerapp.data.model.MUser
import javax.inject.Inject

class AuthRepository @Inject constructor(){
    //TODO: "NOT IMPLEMENTED YET", BusinessLogic will be seperated from LoginViewModel -> SignInWithMailPassword - CreateUser
//    fun signInWithEmailAndPassword(email:String,password:String,auth:FirebaseAuth = Firebase.auth,home: (() -> Unit)? = null){
//        try{
//            auth.signInWithEmailAndPassword(email,password)
//                .addOnCompleteListener{task->
//                    if(task.isSuccessful){
//                        Log.d("FB","signInWithEmailAndPassword RESULT : ${task.result.toString()}")
//                        home?.invoke()
//                    }else{
//                        Log.d("FB","signInWithEmailAndPassword RESUL: ${task.result.toString()}")
//                    }
//                }
//        }catch(e:Exception){
//            Log.d("FB","signInWithEmailAndPassword Exception : ${e.localizedMessage}")
//        }
//    }
    private val auth = FirebaseAuth.getInstance()
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

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }


}
