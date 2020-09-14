package com.khair.appforitis.presentation.profile

import com.khair.appforitis.domain.entity.Profile
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ProfileContract {

    interface Presenter{

        fun getProfile(id: Long)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun showProfile(profile: Profile)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showException(message: String)
        fun openLoginPage()
    }
}