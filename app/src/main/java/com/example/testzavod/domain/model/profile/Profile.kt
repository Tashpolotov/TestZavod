package com.example.testzavod.domain.model.profile

data class Profile (
    val name:String,
    val username:String,
    val birthday:String? = null,
    val city:String? = null,
    val vk:String? = null,
    val instagram:String? = null,
    val status:String? = null,
    val avatar:Ava? = null,
    val id:Int,
    val last:String? = null,
    val online:Boolean? = null,
    val created:String,
    val phone:String,
    val completed_task:Int,
    val avatars:String? = null,
)