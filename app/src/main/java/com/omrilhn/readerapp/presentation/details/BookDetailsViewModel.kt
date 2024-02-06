package com.omrilhn.readerapp.presentation.details

import androidx.lifecycle.ViewModel
import com.omrilhn.readerapp.data.model.Item
import com.omrilhn.readerapp.data.repository.BookRepository
import com.omrilhn.readerapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(private val bookRepository: BookRepository): ViewModel() {

    suspend fun getBookInfo(bookId:String): Resource<Item> {
        return bookRepository.getBookInfo(bookId)

    }
}