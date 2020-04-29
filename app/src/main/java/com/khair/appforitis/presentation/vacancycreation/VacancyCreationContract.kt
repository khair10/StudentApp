package com.khair.appforitis.presentation.vacancycreation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.presentation.vacancycreation.dto.CompanyDto
import com.khair.appforitis.presentation.vacancycreation.dto.VacancyCreationDto

interface VacancyCreationContract {

    interface Presenter{

        fun getCompanies()
        fun addVacancy(item: VacancyCreationDto)
    }

    interface View: MvpView{

        fun fillSpinnerWithCompanies(companies: List<CompanyDto>)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showError(message: String)
        fun finishActivity()
        fun openLoginPage()
    }
}