package com.omrilhn.readerapp.data.repository

import com.omrilhn.readerapp.core.domain.models.DataOrException
import com.omrilhn.readerapp.data.model.Item
import com.omrilhn.readerapp.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookApi:BooksApi) {
    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
    private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()
    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = bookApi.getAllBooks(searchQuery).items
            if (dataOrException.data!!.isNotEmpty()) dataOrException.loading = false
        } catch (e: Exception) {
            dataOrException.e = e
        }
        return dataOrException
    }

    suspend fun getBookInfo(bookdId: String): DataOrException<Item, Boolean, Exception> {
        val response = try {
            bookInfoDataOrException.loading = true
            bookInfoDataOrException.data = bookApi.getBookInfo(bookId = bookdId)
            if (bookInfoDataOrException.data.toString().isNotEmpty()) bookInfoDataOrException.loading = false
            else {

            }
        }catch (e:Exception){
            bookInfoDataOrException.e = e
        }
    }
}