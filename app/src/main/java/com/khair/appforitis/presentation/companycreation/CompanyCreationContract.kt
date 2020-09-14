package com.khair.appforitis.presentation.companycreation

import com.khair.appforitis.presentation.companycreation.dto.CompanyCreationDto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CompanyCreationContract {

    interface Presenter {

        fun addCompany(item: CompanyCreationDto)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
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