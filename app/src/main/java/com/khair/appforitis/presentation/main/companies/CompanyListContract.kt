package com.khair.appforitis.presentation.main.companies

import com.khair.appforitis.presentation.main.companies.dto.CompanyPreviewDto

interface CompanyListContract {

    interface Presenter {

        fun getCompanies()
        fun getCompaniesWithQuery(query: String?)
    }

    interface View {

        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun showCompanies(companies: List<CompanyPreviewDto>)
        fun showEmpty()
        fun openLoginPage()
    }
}