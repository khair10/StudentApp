package com.khair.appforitis.presentation.recallcreation.dto

data class RecallCreationDto(
    val studentItemDto: StudentItemDto,
    val companyItemDto: CompanyItemDto,
    val description: String,
    val rating: Float
) {

    fun isFullFilled(): Boolean = companyItemDto.isFullFilled() && studentItemDto.isFullFilled()
            && description.isNotBlank() && rating != 0f
}