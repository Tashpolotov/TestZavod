package com.example.testzavod.domain.usecase

import com.example.testzavod.domain.model.register.RegisterSend
import com.example.testzavod.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    suspend fun sendRegister(registerSend: RegisterSend) = repository.sendRegister(registerSend)
}