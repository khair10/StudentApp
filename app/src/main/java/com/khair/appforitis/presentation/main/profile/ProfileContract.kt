package com.khair.appforitis.presentation.main.profile

import com.khair.appforitis.domain.entity.Profile
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import java.lang.Exception

interface ProfileContract {

    interface Presenter{

        fun getProfile()
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun showProfile(profile: Profile)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(message: String)
        fun openLoginPage()
    }
}