package com.khair.appforitis.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NetworkLoginForm(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String) : Serializable