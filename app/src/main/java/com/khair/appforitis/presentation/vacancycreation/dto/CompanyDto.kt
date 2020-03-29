package com.khair.appforitis.presentation.vacancycreation.dto

data class CompanyDto(val id: Long, val name: String, val rating: Float, val recallsCount: Int) {

    fun isFullFilled(): Boolean = name.isNotBlank() && id != -1L && rating != 0F && recallsCount != -1

    override fun toString(): String {
        return name
    }
}