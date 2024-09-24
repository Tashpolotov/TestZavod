package com.example.testzavod.domain.usecase

data class UseCase(
    val authUseCase: AuthUseCase,
    val registerUseCase: RegisterUseCase,
    val profileUseCase: ProfileUseCase,
)