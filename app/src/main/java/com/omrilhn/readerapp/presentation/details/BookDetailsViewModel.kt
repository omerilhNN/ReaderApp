package com.omrilhn.readerapp.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.omrilhn.readerapp.data.model.Item
import com.omrilhn.readerapp.data.model.MBook
import com.omrilhn.readerapp.data.repository.BookRepository
import com.omrilhn.readerapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(private val bookRepository: BookRepository): ViewModel() {

    suspend fun getBookInfo(bookId:String): Resource<Item> {
        return bookRepository.getBookInfo(bookId)
    }
    fun saveToFirestore(book:MBook,onNavigate: () -> Unit = {}){
        val db=FirebaseFirestore.getInstance()
        //Create a collection to keep infos
        val dbCollection = db.collection("books")
        if(book.toString().isNotEmpty()){
            dbCollection.add(book)
                .addOnSuccessListener { documentRef->
                    val docId = documentRef.id
                    dbCollection.document(docId)
                        .update(hashMapOf("id" to docId ) as Map<String, Any>)
                        .addOnCompleteListener {task->
                            if(task.isSuccessful){
                                onNavigate()
                            }
                        }.addOnFailureListener{
                            Log.w("Error","SaveToFirebase: Error updating the doc:",it)
                        }
                }
        }else{
            Log.d("Error","book is EMPTY")
        }
    }
}