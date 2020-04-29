package com.khair.appforitis.presentation.main.companies

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.presentation.main.companies.dto.CompanyPreviewDto

interface CompanyListContract {

    interface Presenter {

        fun getCompanies()
        fun getCompaniesWithQuery(query: String?)
    }

    interface View: MvpView {

        fun showLoading()
        fun hideLoading()
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showError(message: String)
        fun showCompanies(companies: List<CompanyPreviewDto>)
        fun showEmpty()
        fun openLoginPage()
    }
}