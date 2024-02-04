package com.omrilhn.readerapp.data.model

data class AccessInfo(
    val accessViewStatus: String,
    val country: String,
    val embeddable: Boolean,
    val epub: com.omrilhn.readerapp.data.model.Epub,
    val pdf: com.omrilhn.readerapp.data.model.Pdf,
    val publicDomain: Boolean,
    val quoteSharingAllowed: Boolean,
    val textToSpeechPermission: String,
    val viewability: String,
    val webReaderLink: String
)