package com.khair.appforitis.presentation.login.dto

data class LoginDto(val login: String?, val password: String?) {

    fun isFullFilled() = login != null && login.isNotBlank() && password != null && password.isNotBlank()
}