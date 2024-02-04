package com.omrilhn.readerapp.data.model

data class Item(
    val accessInfo: com.omrilhn.readerapp.data.model.AccessInfo,
    val etag: String,
    val id: String,
    val kind: String,
    val saleInfo: com.omrilhn.readerapp.data.model.SaleInfo,
    val searchInfo: com.omrilhn.readerapp.data.model.SearchInfo,
    val selfLink: String,
    val volumeInfo: com.omrilhn.readerapp.data.model.VolumeInfo
)