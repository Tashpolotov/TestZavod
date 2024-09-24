package com.example.testzavod.domain.repository

data class Repository(
    val authRepository: AuthRepository,
    val profileRepository: ProfileRepository,
    val registerRepository: RegisterRepository
)
