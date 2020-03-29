package com.khair.appforitis.presentation.recallcreation.dto

class StudentItemDto(val id: Long, val name: String){
    override fun toString(): String {
        return name
    }

    fun isFullFilled(): Boolean = name.isNotBlank() && id != -1L
}