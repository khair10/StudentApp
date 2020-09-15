package com.khair.appforitis.presentation.vacancycreation.dto

data class CompanyDto(val id: Long, val name: String, val rating: Float, val recallsCount: Int) {

    fun isFullFilled(): Boolean = name.isNotBlank() && id != -1L

    override fun toString(): String {
        return name
    }
}