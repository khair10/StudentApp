package com.khair.appforitis.presentation.main.recalls.dto

import java.util.*

data class RecallPreviewDto (
    val id: Long,
    val studentName: String,
    val companyName: String,
    val rating: Float,
    val date: Date
)