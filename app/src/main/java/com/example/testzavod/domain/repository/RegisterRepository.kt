package com.example.testzavod.domain.repository

import com.example.testzavod.domain.model.register.RegisterAnswer
import com.example.testzavod.domain.model.register.RegisterSend
import com.example.testzavod.utils.Resource
import kotlinx.coroutines.flow.Flow


interface RegisterRepository {

    suspend fun sendRegister(register:RegisterSend):Flow<Resource<RegisterAnswer>>
}