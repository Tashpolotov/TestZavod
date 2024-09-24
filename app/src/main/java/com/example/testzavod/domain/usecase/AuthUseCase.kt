package com.example.testzavod.domain.usecase

import com.example.testzavod.domain.model.auth.AuthCode
import com.example.testzavod.domain.model.auth.AuthPhone
import com.example.testzavod.domain.model.register.RegisterSend
import com.example.testzavod.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun sendAuth(phone:AuthPhone) = repository.sendAuth(phone)

    suspend fun sendCode(code:AuthCode) = repository.sendCode(code)

}