package com.khair.appforitis.presentation.main.vacancies.dto

import java.util.*

data class VacancyPreviewDto(
    val id: Long,
    val title: String,
    val companyName: String,
    val preview: String,
    val date: Date
)
