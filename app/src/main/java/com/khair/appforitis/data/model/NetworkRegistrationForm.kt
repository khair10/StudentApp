package com.khair.appforitis.data.model

import java.io.Serializable

data class NetworkRegistrationForm(
    val login: String,
    val password: String,
    val passwordConfirm: String,
    val profile: NetworkProfileItem
) : Serializable