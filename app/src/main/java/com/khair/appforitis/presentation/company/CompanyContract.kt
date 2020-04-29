package com.khair.appforitis.presentation.company

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.domain.entity.Company

interface CompanyContract {

    interface Presenter{

        fun getCompany(id: Long)
    }

    interface View: MvpView{

        fun showCompany(company: Company)
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showException(message: String)
        fun showLoading()
        fun hideLoading()
        fun openLoginPage()
    }
}