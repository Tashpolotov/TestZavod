package com.example.testzavod.data.model.profile

import com.example.testzavod.data.model.profile.ProfileData
import com.google.gson.annotations.SerializedName

data class ProfileModelData(
    @SerializedName("profile_data")
    val profileData : ProfileData
)
