package com.omrilhn.readerapp.data.model

data class VolumeInfo(
    val allowAnonLogging: Boolean,
    val authors: List<String>,
    val averageRating: Int,
    val canonicalVolumeLink: String,
    val categories: List<String>,
    val contentVersion: String,
    val description: String,
    val imageLinks: com.omrilhn.readerapp.data.model.ImageLinks,
    val industryIdentifiers: List<com.omrilhn.readerapp.data.model.IndustryIdentifier>,
    val infoLink: String,
    val language: String,
    val maturityRating: String,
    val pageCount: Int,
    val panelizationSummary: PanelizationSummary,
    val previewLink: String,
    val printType: String,
    val publishedDate: String,
    val publisher: String,
    val ratingsCount: Int,
    val readingModes: ReadingModes,
    val subtitle: String,
    val title: String
)