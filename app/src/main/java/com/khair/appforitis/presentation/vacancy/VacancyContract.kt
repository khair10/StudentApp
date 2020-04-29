package com.khair.appforitis.presentation.vacancy

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.domain.entity.Vacancy

interface VacancyContract {

    interface Presenter {

        fun getVacancy(id: Long)
    }

    interface View: MvpView {

        fun showVacancy(vacancy: Vacancy)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showException(message: String)
        fun openLoginPage()
    }
}