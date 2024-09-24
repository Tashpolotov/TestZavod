package com.example.testzavod.domain.repository

import com.example.testzavod.data.model.profile.ProfileModelData
import com.example.testzavod.domain.model.profile.AnswerChange
import com.example.testzavod.domain.model.profile.ChangeData
import com.example.testzavod.domain.model.profile.ProfileModel
import com.example.testzavod.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile():Flow<Resource<ProfileModel>>

    suspend fun sendChange(change:ChangeData) : Flow<Resource<AnswerChange>>

}