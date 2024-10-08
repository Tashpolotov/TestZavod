package com.example.testzavod.domain.repository

import com.example.testzavod.domain.either.Either
import com.example.testzavod.domain.model.profile.AnswerChange
import com.example.testzavod.domain.model.profile.ChangeData
import com.example.testzavod.domain.model.profile.ProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile():Flow<Either<String, ProfileModel>>

    suspend fun sendChange(change:ChangeData) : Flow<Either<String, AnswerChange>>

}