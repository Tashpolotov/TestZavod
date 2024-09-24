package com.example.testzavod.data.repository

import com.example.testzavod.data.mapper.toRegisterAnswerData
import com.example.testzavod.data.remote.RegisterApiService
import com.example.testzavod.domain.model.register.RegisterAnswer
import com.example.testzavod.domain.model.register.RegisterSend
import com.example.testzavod.domain.repository.RegisterRepository
import com.example.testzavod.utils.Resource
import com.example.testzavod.utils.SharedPref
import kg.geekstudio.core_utils.base.BaseRepository
import kotlinx.coroutines.flow.Flow

class RegisterRepoImpl(
    private val apiService: RegisterApiService,
    private val sharedPref: SharedPref
):BaseRepository(), RegisterRepository {
    override suspend fun sendRegister(register: RegisterSend) = doRequest {
        val response = apiService.sendRegister(register).toRegisterAnswerData()
        sharedPref.accessToken= response.accessToken
        sharedPref.refreshToken= response.refreshToken
        response
    }
}