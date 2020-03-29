package com.khair.appforitis.data.model

import java.util.*

data class NetworkVacancy(
    val id: Long,
    val name: String,
    val information: String,
    val company: NetworkCompanyItemDto,
    val rating: Float,
    val recallsCount: Int,
    val salary: Int,
    val student: NetworkStudentItemDto,
    val date: Date
)