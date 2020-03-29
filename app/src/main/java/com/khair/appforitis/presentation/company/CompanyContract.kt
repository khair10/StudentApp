package com.khair.appforitis.presentation.company

import com.khair.appforitis.domain.entity.Company

interface CompanyContract {

    interface Presenter{

        fun getCompany(id: Long)
    }

    interface View{

        fun showCompany(company: Company)
        fun showException(message: String)
        fun showLoading()
        fun hideLoading()
        fun openLoginPage()
    }
}