package com.khair.appforitis.presentation.vacancycreation

import com.khair.appforitis.presentation.vacancycreation.dto.CompanyDto
import com.khair.appforitis.presentation.vacancycreation.dto.VacancyCreationDto

interface VacancyCreationContract {

    interface Presenter{

        fun getCompanies()
        fun addVacancy(item: VacancyCreationDto)
    }

    interface View{

        fun fillSpinnerWithCompanies(companies: List<CompanyDto>)
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun finishActivity()
        fun openLoginPage()
    }
}