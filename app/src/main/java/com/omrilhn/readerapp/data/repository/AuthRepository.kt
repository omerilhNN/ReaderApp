package com.omrilhn.readerapp.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.omrilhn.readerapp.utils.SimpleResource

class AuthRepository {
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
}
