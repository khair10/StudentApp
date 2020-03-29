package com.khair.appforitis.presentation.vacancycreation.dto

class StudentDto(val id: Long, val name: String){
    override fun toString(): String {
        return name
    }

    fun isFullFilled(): Boolean = name.isNotBlank() && id != -1L
}
