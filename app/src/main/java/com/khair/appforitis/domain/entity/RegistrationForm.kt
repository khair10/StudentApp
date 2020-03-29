package com.khair.appforitis.domain.entity

class RegistrationForm(
    val login: String,
    val password: String,
    val passwordConfirm: String,
    val profile: ProfileItem
) {
}