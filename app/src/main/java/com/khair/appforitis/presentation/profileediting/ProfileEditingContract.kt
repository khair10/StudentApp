package com.khair.appforitis.presentation.profileediting

import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.presentation.profileediting.dto.CompanyDto
import com.khair.appforitis.presentation.profileediting.dto.ProfileDto

interface ProfileEditingContract {

    interface Presenter{

        fun getCompanies()
        fun getProfile()
        fun setProfile(profile: ProfileDto)
    }

    interface View{

        fun fillSpinnerWithCompanies(companies: List<CompanyDto>)
        fun showLoading()
        fun hideLoading()
        fun showProfile(profile: Profile)
        fun showError(message: String)
        fun finishActivity()
        fun openLoginPage()
    }
}