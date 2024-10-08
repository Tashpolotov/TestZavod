package com.example.testzavod.data.repository

import com.example.testzavod.data.mapper.toRegisterAnswerData
import com.example.testzavod.data.remote.RegisterApiService
import com.example.testzavod.domain.model.register.RegisterSend
import com.example.testzavod.domain.repository.RegisterRepository
import com.example.testzavod.utils.SharedPref
import com.example.testzavod.utils.base.BaseRepository

class RegisterRepoImpl(
    private val apiService: RegisterApiService,
    private val sharedPref: SharedPref
): BaseRepository(), RegisterRepository {
    override suspend fun sendRegister(register: RegisterSend) = doRequest {
        val response = apiService.sendRegister(register).toRegisterAnswerData()
        sharedPref.accessToken= response.accessToken
        sharedPref.refreshToken= response.refreshToken
        response
    }
}