package com.example.testzavod.data.model.profile

import com.example.testzavod.domain.model.profile.Ava

data class ProfileData (
    val name:String? = null,
    val username:String? = null,
    var birthday:String? = null,
    var city:String? = null,
    val vk:String? = null,
    val instagram:String? = null,
    val status:String? = null,
    val avatar:String? = null,
    val id:Int? = null,
    val last:String? = null,
    val online:Boolean? = null,
    val created:String? = null,
    val phone:String? = null,
    val completed_task:Int? = null,
    val avatars:Ava? = null,
)