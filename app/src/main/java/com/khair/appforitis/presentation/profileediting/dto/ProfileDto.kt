package com.khair.appforitis.presentation.profileediting.dto

data class ProfileDto(
    val name: String,
//    val companyId: Long,
//    val company: String,
    val company: CompanyDto,
    val phone: String,
    val vk: String,
    val telegram: String,
    val facebook: String,
    val additionalDescription: String) {

    fun isFullFilled() = name.isNotBlank() &&
            (phone.isNotBlank() || vk.isNotBlank() ||
                    telegram.isNotBlank() || facebook.isNotBlank())
}