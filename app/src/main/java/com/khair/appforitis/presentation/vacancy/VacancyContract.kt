package com.khair.appforitis.presentation.vacancy

import com.khair.appforitis.domain.entity.Vacancy
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface VacancyContract {

    interface Presenter {

        fun getVacancy(id: Long)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun showVacancy(vacancy: Vacancy)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showException(message: String)
        fun openLoginPage()
    }
}