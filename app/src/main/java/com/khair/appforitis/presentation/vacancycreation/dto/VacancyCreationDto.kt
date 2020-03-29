package com.khair.appforitis.presentation.vacancycreation.dto

data class VacancyCreationDto(
    val name: String,
    val salary: String,
    val companyDto: CompanyDto,
    val description: String,
    val studentDto: StudentDto
) {

    fun isFullFilled(): Boolean = name.isNotBlank()
            && salary.isNotBlank() && salary.toIntOrNull() != null
            && companyDto.isFullFilled()
            && description.isNotBlank()
}