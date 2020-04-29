package com.khair.appforitis.presentation.main.profile

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.domain.entity.Profile

interface ProfileContract {

    interface Presenter{

        fun getProfile()
    }

    interface View: MvpView{

        fun showProfile(profile: Profile)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showError(message: String)
        fun openLoginPage()
    }
}