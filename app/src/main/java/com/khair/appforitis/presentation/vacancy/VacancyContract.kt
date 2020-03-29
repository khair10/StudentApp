package com.khair.appforitis.presentation.vacancy

import com.khair.appforitis.domain.entity.Vacancy

interface VacancyContract {

    interface Presenter {

        fun getVacancy(id: Long)
    }

    interface View {

        fun showVacancy(vacancy: Vacancy)
        fun showLoading()
        fun hideLoading()
        fun showException(message: String)
        fun openLoginPage()
    }
}