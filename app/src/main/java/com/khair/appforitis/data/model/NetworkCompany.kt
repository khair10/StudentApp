package com.khair.appforitis.data.model

data class NetworkCompany(
    val id: Long,
    val name: String,
    val address: String,
    val website: String,
    val phone: String,
    val information: String,
    val rating: Float,
    val recallsCount: Int
)