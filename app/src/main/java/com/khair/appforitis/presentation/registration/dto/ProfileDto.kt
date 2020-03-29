package com.khair.appforitis.presentation.registration.dto

data class ProfileDto(
    val name: String,
    val phone: String
) {

    fun isFullFilled() = name.isNotBlank() && phone.isNotBlank()
}