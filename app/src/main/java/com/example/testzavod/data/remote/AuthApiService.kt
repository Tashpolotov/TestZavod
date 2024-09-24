package com.example.testzavod.data.remote

import com.example.testzavod.data.model.RegisterAnswerData
import com.example.testzavod.domain.model.auth.AnswerAuthPhone
import com.example.testzavod.domain.model.auth.AuthCode
import com.example.testzavod.domain.model.auth.AuthPhone
import com.example.testzavod.domain.model.register.RegisterSend
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("api/v1/users/send-auth-code/")
    suspend fun sendAuth(
        @Body phone: AuthPhone
    ): AnswerAuthPhone

    @POST("api/v1/users/check-auth-code/")
    suspend fun sendCode(
        @Body code: AuthCode,
    ): RegisterAnswerData
}