package com.example.testzavod.domain.repository

import com.example.testzavod.domain.either.Either
import com.example.testzavod.domain.model.auth.AnswerAuthPhone
import com.example.testzavod.domain.model.auth.AuthCode
import com.example.testzavod.domain.model.auth.AuthPhone
import com.example.testzavod.domain.model.register.RegisterAnswer
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun sendAuth(phone:AuthPhone):Flow<Either<String, AnswerAuthPhone>>

    suspend fun sendCode(code:AuthCode):Flow<Either<String, RegisterAnswer>>

}