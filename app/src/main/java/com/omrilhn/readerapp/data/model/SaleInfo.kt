package com.omrilhn.readerapp.data.model

data class SaleInfo(
    val buyLink: String,
    val country: String,
    val isEbook: Boolean,
    val listPrice: com.omrilhn.readerapp.data.model.ListPrice,
    val offers: List<Offer>,
    val retailPrice: RetailPriceX,
    val saleability: String
)