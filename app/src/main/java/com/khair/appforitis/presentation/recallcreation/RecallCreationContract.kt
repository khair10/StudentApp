package com.khair.appforitis.presentation.recallcreation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.presentation.recallcreation.dto.CompanyItemDto
import com.khair.appforitis.presentation.recallcreation.dto.RecallCreationDto

interface RecallCreationContract {

    interface Presenter{

        fun getCompanies()
        fun addRecall(item: RecallCreationDto)
    }

    interface View: MvpView{

        fun fillSpinnerWithCompanies(companies: List<CompanyItemDto>)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showError(message: String)
        fun finishActivity()
        fun openLoginPage()
    }
}