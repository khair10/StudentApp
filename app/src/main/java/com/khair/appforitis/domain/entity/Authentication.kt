package com.khair.appforitis.domain.entity

data class Authentication(
//TODO change, id and name to student item object
    val id: Long,
    val name: String,
    val jsonToken: String,
    val refreshToken: String
) {
}