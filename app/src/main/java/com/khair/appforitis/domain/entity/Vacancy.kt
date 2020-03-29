package com.khair.appforitis.domain.entity

import java.util.*

data class Vacancy(
    val id: Long,
    val name: String,
    val information: String,
    val company: CompanyItem,
    val rating: Float,
    val recallsCount: Int,
    val salary: Int,
    val student: StudentItem,
    val date: Date
)