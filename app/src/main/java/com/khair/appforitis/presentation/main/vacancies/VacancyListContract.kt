package com.khair.appforitis.presentation.main.vacancies

import com.khair.appforitis.presentation.main.vacancies.dto.VacancyPreviewDto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface VacancyListContract {

    interface Presenter {

        fun getVacancies()
        fun getVacancies(query: String?)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun showLoading()
        fun hideLoading()
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(message: String)
        fun showVacancies(vacancies: List<VacancyPreviewDto>)
        fun showEmpty()
        fun openLoginPage()
    }
}