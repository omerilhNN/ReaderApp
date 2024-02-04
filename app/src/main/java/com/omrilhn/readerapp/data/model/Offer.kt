package com.omrilhn.readerapp.data.model

data class Offer(
    val finskyOfferType: Int,
    val listPrice: com.omrilhn.readerapp.data.model.ListPriceX,
    val retailPrice: RetailPrice
)