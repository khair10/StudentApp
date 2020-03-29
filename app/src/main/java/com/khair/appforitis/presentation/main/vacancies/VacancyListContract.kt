package com.khair.appforitis.presentation.main.vacancies

import com.khair.appforitis.presentation.main.vacancies.dto.VacancyPreviewDto

interface VacancyListContract {

    interface Presenter {

        fun getVacancies()
        fun getVacancies(query: String?)
    }

    interface View {

        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun showVacancies(vacancies: List<VacancyPreviewDto>)
        fun showEmpty()
        fun openLoginPage()
    }
}