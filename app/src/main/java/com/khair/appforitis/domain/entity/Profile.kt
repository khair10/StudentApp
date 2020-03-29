package com.khair.appforitis.domain.entity

data class Profile(
    val id: Long,
    val name: String,
    val company: CompanyItem,
    val phone: String,
    val vk: String,
    val telegram: String,
    val facebook: String,
    val additionalInformation: String
)