package com.omrilhn.readerapp.network

import com.omrilhn.readerapp.data.model.Book
import com.omrilhn.readerapp.data.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

//One instance of this interface for activity lifecycle
@Singleton
interface BooksApi {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): com.omrilhn.readerapp.data.model.Book

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId:String): com.omrilhn.readerapp.data.model.Item
}