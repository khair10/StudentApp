package com.khair.appforitis.presentation.registration.dto

data class RegistrationDto(
    val login: String,
    val password: String,
    val passwordConfirm: String,
    val profile: ProfileDto
) {
    fun isFullFilled() = login.isNotBlank() && password.isNotBlank() &&
            passwordConfirm.isNotBlank() && password == passwordConfirm &&
            profile.isFullFilled()
}