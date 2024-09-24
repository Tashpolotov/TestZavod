package com.example.testzavod.domain.usecase

import com.example.testzavod.domain.model.profile.ChangeData
import com.example.testzavod.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend fun getProfile() = repository.getProfile()

    suspend fun sendChange(change:ChangeData) = repository.sendChange(change)
}