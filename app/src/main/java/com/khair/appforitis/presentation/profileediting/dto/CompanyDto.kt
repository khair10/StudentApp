package com.khair.appforitis.presentation.profileediting.dto

data class CompanyDto(val id: Long, val name: String){
    override fun toString(): String {
        return name
    }
}