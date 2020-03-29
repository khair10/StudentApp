package com.khair.appforitis.data.model

data class NetworkProfile(
    val id: Long,
    val name: String,
    val company: NetworkCompanyItemDto,
    val phone: String,
    val vk: String,
    val telegram: String,
    val facebook: String,
    val additionalInformation: String
)