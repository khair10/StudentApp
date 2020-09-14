package com.khair.appforitis.presentation.main.companies

import com.khair.appforitis.presentation.main.companies.dto.CompanyPreviewDto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CompanyListContract {

    interface Presenter {

        fun getCompanies()
        fun getCompaniesWithQuery(query: String?)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun showLoading()
        fun hideLoading()
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(message: String)
        fun showCompanies(companies: List<CompanyPreviewDto>)
        fun showEmpty()
        fun openLoginPage()
    }
}