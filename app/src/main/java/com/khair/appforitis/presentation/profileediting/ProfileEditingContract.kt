package com.khair.appforitis.presentation.profileediting

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.presentation.profileediting.dto.CompanyDto
import com.khair.appforitis.presentation.profileediting.dto.ProfileDto

interface ProfileEditingContract {

    interface Presenter{

        fun getCompanies()
        fun getProfile()
        fun saveProfile(profile: ProfileDto)
    }

    interface View: MvpView{

        fun fillSpinnerWithCompanies(companies: List<CompanyDto>)
        fun showLoading()
        fun hideLoading()
        fun showProfile(profile: Profile)
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showError(message: String)
        fun finishActivity()
        fun openLoginPage()
    }
}