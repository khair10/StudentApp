package com.khair.appforitis.presentation.recallcreation.dto

data class CompanyItemDto(val id: Long, val name: String){
    override fun toString(): String {
        return name
    }

    fun isFullFilled(): Boolean = name.isNotBlank() && id != -1L
}