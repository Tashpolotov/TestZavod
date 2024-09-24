package com.example.testzavod.domain.model.profile

import com.example.testzavod.data.model.profile.ProfileData
import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("profile_data")
    val profileData : ProfileData
)
