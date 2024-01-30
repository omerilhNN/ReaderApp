package com.omrilhn.readerapp.core.domain.models

// id is VAR because we change Firestore by adding into it.
data class MBook(
    var id:String? = null,
    var title:String? = null,
    var authors:String?=null,
    var notes:String? = null


)