package com.example.testzavod.data.remote

import retrofit2.Call
import com.example.testzavod.data.model.RegisterAnswerData
import com.example.testzavod.domain.model.register.RegisterSend
import com.example.testzavod.domain.model.token.RefreshToken
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {

    @POST("api/v1/users/register/")
    suspend fun sendRegister(
        @Body register:RegisterSend
    ):RegisterAnswerData

    @POST("api/v1/users/refresh-token/")
    fun sendRefreshToken(
        @Body refreshToken: RefreshToken
    ): Call<RegisterAnswerData>

}