package com.example.testzavod.domain.repository

import com.example.testzavod.domain.model.auth.AnswerAuthPhone
import com.example.testzavod.domain.model.auth.AuthCode
import com.example.testzavod.domain.model.auth.AuthPhone
import com.example.testzavod.domain.model.register.RegisterAnswer
import com.example.testzavod.domain.model.register.RegisterSend
import com.example.testzavod.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun sendAuth(phone:AuthPhone):Flow<Resource<AnswerAuthPhone>>

    suspend fun sendCode(code:AuthCode):Flow<Resource<RegisterAnswer>>

}