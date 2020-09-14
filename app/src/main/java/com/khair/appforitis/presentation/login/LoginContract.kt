package com.khair.appforitis.presentation.login

import com.khair.appforitis.presentation.login.dto.LoginDto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface LoginContract {

    interface Presenter{

        fun login(loginForm: LoginDto)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(message: String)
        fun showLoading()
        fun hideLoading()
        fun openHome()
    }
}