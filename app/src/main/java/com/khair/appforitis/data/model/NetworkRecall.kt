package com.khair.appforitis.data.model

import java.util.*

data class NetworkRecall(
    val id: Long,
    val student: NetworkStudentItemDto,
    val information: String,
    val company: NetworkCompanyItemDto,
    val rating: Float,
    val date: Date
)