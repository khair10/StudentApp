package com.khair.appforitis.domain.entity

import java.util.*

data class Recall(
    val id: Long,
    val student: StudentItem,
    val information: String,
    val company: CompanyItem,
    val rating: Float,
    val date: Date
//
//    //temp
//    val companyName: String,
//
//    //future
//    val company: Company
)