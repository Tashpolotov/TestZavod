package com.example.testzavod.domain.repository

import com.example.testzavod.domain.either.Either
import com.example.testzavod.domain.model.register.RegisterAnswer
import com.example.testzavod.domain.model.register.RegisterSend
import kotlinx.coroutines.flow.Flow


interface RegisterRepository {

    suspend fun sendRegister(register:RegisterSend):Flow<Either<String, RegisterAnswer>>
}