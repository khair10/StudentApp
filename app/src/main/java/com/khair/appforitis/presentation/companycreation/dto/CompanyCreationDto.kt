package com.khair.appforitis.presentation.companycreation.dto

data class CompanyCreationDto(
    val name: String,
    val address: String,
    val webSite: String,
    val contactNumber: String,
    val info: String
) {

    fun isFullFilled(): Boolean = name.isNotBlank() && address.isNotBlank() && webSite.isNotBlank()
            && contactNumber.isNotBlank() && info.isNotBlank()
}