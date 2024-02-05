package com.omrilhn.readerapp.presentation.search

import com.omrilhn.readerapp.data.model.Item
import com.omrilhn.readerapp.utils.Resource

data class SearchState(
    val listOfBooks:List<Item> = emptyList(),
    var isLoading: Boolean = false
)
