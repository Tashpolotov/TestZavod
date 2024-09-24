package com.example.testzavod.data.mapper

import com.example.testzavod.data.model.RegisterAnswerData
import com.example.testzavod.data.model.profile.AnswerChangeData
import com.example.testzavod.data.model.profile.ProfileData
import com.example.testzavod.data.model.profile.ProfileModelData
import com.example.testzavod.domain.model.profile.AnswerChange
import com.example.testzavod.domain.model.profile.Profile
import com.example.testzavod.domain.model.profile.ProfileModel
import com.example.testzavod.domain.model.register.RegisterAnswer

fun RegisterAnswerData.toRegisterAnswerData() = RegisterAnswer(
    refreshToken, accessToken, userId, isUserExists
)


fun ProfileModelData.toProfileData() = ProfileModel(
    profileData
)

fun AnswerChangeData.toAnswerChange() = AnswerChange(
    avatars
)