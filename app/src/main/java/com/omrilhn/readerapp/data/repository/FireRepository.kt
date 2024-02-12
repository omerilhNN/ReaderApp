package com.omrilhn.readerapp.data.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.data.model.MBook
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(private val queryBook:Query) {
    suspend fun getAllBooksFromDatabase():DataOrException<List<MBook>,Boolean,Exception>{
        val dataOrException = DataOrException<List<MBook>,Boolean,Exception>()

        try{
            dataOrException.loading = true
            //Awaits the completion of a task without BLOCKING the thread
            dataOrException.data = queryBook.get().await().documents.map {documentSnapshot ->
                //Transforming documnet data to MBook object
                documentSnapshot.toObject(MBook::class.java)!!
            }
            if(!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false
            
        }catch (e: FirebaseFirestoreException){
            dataOrException.e = e
        }
        return dataOrException
    }

}