package com.khair.appforitis.domain.entity

data class Authentication(
    val id: Long,
    val name: String,
    val jsonToken: String,
    val refreshToken: String
) {
}