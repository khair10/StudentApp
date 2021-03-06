package com.khair.appforitis.presentation.recallcreation

import com.khair.appforitis.presentation.recallcreation.dto.CompanyItemDto
import com.khair.appforitis.presentation.recallcreation.dto.RecallCreationDto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface RecallCreationContract {

    interface Presenter{

        fun getCompanies()
        fun addRecall(item: RecallCreationDto)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun fillSpinnerWithCompanies(companies: List<CompanyItemDto>)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(message: String)
        fun finishActivity()
        fun openLoginPage()
    }
}