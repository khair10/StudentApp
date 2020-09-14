package com.khair.appforitis.presentation.profileediting

import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.presentation.profileediting.dto.CompanyDto
import com.khair.appforitis.presentation.profileediting.dto.ProfileDto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ProfileEditingContract {

    interface Presenter{

        fun getCompanies()
        fun getProfile()
        fun saveProfile(profile: ProfileDto)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun fillSpinnerWithCompanies(companies: List<CompanyDto>)
        fun showLoading()
        fun hideLoading()
        fun showProfile(profile: Profile)
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(message: String)
        fun finishActivity()
        fun openLoginPage()
    }
}