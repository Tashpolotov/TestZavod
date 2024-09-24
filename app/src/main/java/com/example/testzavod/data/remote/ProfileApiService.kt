package com.example.testzavod.data.remote

import com.example.testzavod.data.model.profile.AnswerChangeData
import com.example.testzavod.data.model.profile.ProfileModelData
import com.example.testzavod.domain.model.profile.AnswerChange
import com.example.testzavod.domain.model.profile.ChangeData
import com.example.testzavod.domain.model.profile.ProfileModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApiService {


    @GET("api/v1/users/me/")
    suspend fun getProfile(

    ): ProfileModelData

    @PUT("api/v1/users/me/")
    suspend fun sendChange(
        @Body change:ChangeData
    ): AnswerChangeData
}