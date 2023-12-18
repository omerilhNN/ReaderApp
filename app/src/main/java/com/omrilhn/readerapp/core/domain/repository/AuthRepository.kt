package com.omrilhn.readerapp.core.domain.repository

import com.omrilhn.readerapp.utils.SimpleResource

interface AuthRepository {
    suspend fun register(
        email:String,
        password:String
    ):SimpleResource
    suspend fun login(
        email:String,
        password:String
    ):SimpleResource

    suspend fun authenticate():SimpleResource
}
