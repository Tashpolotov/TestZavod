package com.example.testzavod.data.repository

import com.example.testzavod.data.mapper.toRegisterAnswerData
import com.example.testzavod.data.remote.AuthApiService
import com.example.testzavod.domain.model.auth.AuthCode
import com.example.testzavod.domain.model.auth.AuthPhone
import com.example.testzavod.domain.model.register.RegisterAnswer
import com.example.testzavod.domain.repository.AuthRepository
import com.example.testzavod.utils.Resource
import com.example.testzavod.utils.SharedPref
import kg.geekstudio.core_utils.base.BaseRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val apiService: AuthApiService,
    private val sharedPref: SharedPref
) :AuthRepository, BaseRepository(){
    override suspend fun sendAuth(phone: AuthPhone) = doRequest {
        apiService.sendAuth(phone)
    }

    override suspend fun sendCode(code: AuthCode) = doRequest {
        val response = apiService.sendCode(code).toRegisterAnswerData()
        sharedPref.accessToken= response.accessToken
        sharedPref.refreshToken= response.refreshToken
        response
    }
}