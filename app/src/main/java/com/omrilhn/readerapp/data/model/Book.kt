package com.omrilhn.readerapp.data.model

data class Book(
    val items: List<com.omrilhn.readerapp.data.model.Item>,
    val kind: String,
    val totalItems: Int
)