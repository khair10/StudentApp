package com.khair.appforitis.presentation.companycreation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.presentation.companycreation.dto.CompanyCreationDto

interface CompanyCreationContract {

    interface Presenter {

        fun addCompany(item: CompanyCreationDto)
    }

    interface View: MvpView {
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(message: String)
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun finishActivity()
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun openLoginPage()
    }
}