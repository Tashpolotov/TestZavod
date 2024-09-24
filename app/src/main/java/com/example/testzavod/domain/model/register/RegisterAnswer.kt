package com.example.testzavod.domain.model.register

import com.google.gson.annotations.SerializedName

data class RegisterAnswer(
    @SerializedName("refresh_token")
    val refreshToken : String,
    @SerializedName("access_token")
    val accessToken : String,
    @SerializedName("user_id")
    val userId:Int,
    @SerializedName("is_user_exists")
    val isUserExists : Boolean


)