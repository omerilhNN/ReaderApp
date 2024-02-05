package com.omrilhn.readerapp.data.repository

import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.data.model.Item
import com.omrilhn.readerapp.network.BooksApi
import com.omrilhn.readerapp.utils.Resource
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookApi:BooksApi) {

    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        return try {
           Resource.Loading(data = true)

            val itemList = bookApi.getAllBooks(searchQuery).items
            if(itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)

        } catch (e: Exception) {
                Resource.Error(message = e.message.toString())
        }
    }

    suspend fun getBookInfo(bookdId: String):   Resource<Item> {
        val response = try {
           Resource.Loading(data = true )
            bookApi.getBookInfo(bookdId)
        }catch (e:Exception){
            return Resource.Error(message = "EXCEPTION: ${e.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}