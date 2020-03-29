package com.khair.appforitis.presentation.recallcreation

import com.khair.appforitis.presentation.recallcreation.dto.CompanyItemDto
import com.khair.appforitis.presentation.recallcreation.dto.RecallCreationDto

interface RecallCreationContract {

    interface Presenter{

        fun getCompanies()
        fun addRecall(item: RecallCreationDto)
    }

    interface View{

        fun fillSpinnerWithCompanies(companies: List<CompanyItemDto>)
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun finishActivity()
        fun openLoginPage()
    }
}