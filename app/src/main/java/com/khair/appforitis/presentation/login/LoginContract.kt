package com.khair.appforitis.presentation.login

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.presentation.login.dto.LoginDto

interface LoginContract {

    interface Presenter{

        fun login(loginForm: LoginDto)
    }

    interface View: MvpView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showError(message: String)
        fun showLoading()
        fun hideLoading()
        fun openHome()
    }
}