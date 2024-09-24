package com.example.testzavod.domain.model.profile

data class ChangeData(
    val name: String? = null,
    val username: String? = null,
    val birthday: String? = null,
    val city: String? = null,
    val vk: String? = null,
    val instagram: String? = null,
    val status: String? = null,
    val avatar: Avatar? = null
)