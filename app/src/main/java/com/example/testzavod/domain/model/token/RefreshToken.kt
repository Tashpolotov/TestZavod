package com.example.testzavod.domain.model.token

import com.google.gson.annotations.SerializedName

data class RefreshToken(
    @SerializedName("refresh_token")
    val refreshToken:String,

)