package com.khair.appforitis.data.model

data class NetworkRegistrationForm(
    val login: String,
    val password: String,
    val passwordConfirm: String,
    val profile: NetworkProfileItem
) {
}