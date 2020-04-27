package com.khair.appforitis.data.model

import com.google.gson.annotations.SerializedName

data class NetworkAuthentication(
    @SerializedName("studentItemDto")
    val studentItemDto: NetworkStudentItemDto,
    val jwtToken: String,
    val refreshToken: String
) {
}