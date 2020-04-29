package com.khair.appforitis.presentation.main.vacancies

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.presentation.main.vacancies.dto.VacancyPreviewDto

interface VacancyListContract {

    interface Presenter {

        fun getVacancies()
        fun getVacancies(query: String?)
    }

    interface View: MvpView {

        fun showLoading()
        fun hideLoading()
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showError(message: String)
        fun showVacancies(vacancies: List<VacancyPreviewDto>)
        fun showEmpty()
        fun openLoginPage()
    }
}