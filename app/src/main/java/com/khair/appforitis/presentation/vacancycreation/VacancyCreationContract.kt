package com.khair.appforitis.presentation.vacancycreation

import com.khair.appforitis.presentation.vacancycreation.dto.CompanyDto
import com.khair.appforitis.presentation.vacancycreation.dto.VacancyCreationDto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface VacancyCreationContract {

    interface Presenter{

        fun getCompanies()
        fun addVacancy(item: VacancyCreationDto)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun fillSpinnerWithCompanies(companies: List<CompanyDto>)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(message: String)
        fun finishActivity()
        fun openLoginPage()
    }
}