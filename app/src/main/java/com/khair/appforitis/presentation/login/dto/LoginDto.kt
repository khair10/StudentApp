package com.khair.appforitis.presentation.login.dto

data class LoginDto(val login: String, val password: String) {

    fun isFullFilled() = login.isNotBlank() || password.isNotBlank()
}