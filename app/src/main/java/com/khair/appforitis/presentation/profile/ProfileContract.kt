package com.khair.appforitis.presentation.profile

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.domain.entity.Profile

interface ProfileContract {

    interface Presenter{

        fun getProfile(id: Long)
    }

    interface View: MvpView{

        fun showProfile(profile: Profile)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showException(message: String)
        fun openLoginPage()
    }
}