package com.khair.appforitis.presentation.company

import com.khair.appforitis.domain.entity.Company
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

interface CompanyContract {

    interface Presenter{

        fun getCompany(id: Long)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun showCompany(company: Company)
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showException(message: String)
        fun showLoading()
        fun hideLoading()
        fun openLoginPage()
    }
}