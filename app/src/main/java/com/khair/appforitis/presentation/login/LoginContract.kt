package com.khair.appforitis.presentation.login

import com.khair.appforitis.presentation.login.dto.LoginDto

interface LoginContract {

    interface Presenter{

        fun login(loginForm: LoginDto)
    }

    interface View {

        fun showError(message: String)
        fun showLoading()
        fun hideLoading()
        fun openHome()
    }
}