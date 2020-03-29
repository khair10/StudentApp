package com.khair.appforitis.presentation.companycreation

import com.khair.appforitis.presentation.companycreation.dto.CompanyCreationDto

interface CompanyCreationContract {

    interface Presenter {

        fun addCompany(item: CompanyCreationDto)
    }

    interface View {

        fun showError(message: String)
        fun finishActivity()
        fun showLoading()
        fun hideLoading()
        fun openLoginPage()
    }
}