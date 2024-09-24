package com.example.testzavod.data.repository

import com.example.testzavod.data.mapper.toAnswerChange
import com.example.testzavod.data.mapper.toProfileData
import com.example.testzavod.data.model.profile.ProfileModelData
import com.example.testzavod.data.remote.ProfileApiService
import com.example.testzavod.domain.model.profile.AnswerChange
import com.example.testzavod.domain.model.profile.ChangeData
import com.example.testzavod.domain.model.profile.ProfileModel
import com.example.testzavod.domain.repository.ProfileRepository
import com.example.testzavod.utils.Resource
import kg.geekstudio.core_utils.base.BaseRepository
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(
    private val apiService: ProfileApiService,

):ProfileRepository, BaseRepository() {
    override suspend fun getProfile() = doRequest {
        apiService.getProfile().toProfileData()
    }

    override suspend fun sendChange(change: ChangeData) = doRequest {
        apiService.sendChange(change).toAnswerChange()
    }
}