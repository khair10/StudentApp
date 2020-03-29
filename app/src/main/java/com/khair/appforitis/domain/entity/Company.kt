package com.khair.appforitis.domain.entity

data class Company(
    val id: Long,
    val name: String,
    val address: String,
    val website: String,
    val phone: String,
    val information: String,
    val rating: Float,
    val recallsCount: Int

//    val recalls: List<Recall>? = null,
//    val vacancies: List<Vacancy>? = null

//    //future
//    val logoLink: String,
//    val photosLinks: List<String>,
//    val recalls: List<recall>,
//    val vacancies: List<vacancy>,
//
//    //back-end variables
//    val madeBy: Long, //id
//    val approvedBy: Long, //id
//    val madeDate: Long
)