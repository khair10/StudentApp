package com.khair.appforitis.presentation.registration

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.presentation.registration.dto.RegistrationDto

interface RegistrationContract {

    interface Presenter{

        fun registration(registrationForm: RegistrationDto)
    }

    interface View: MvpView {

        fun openLogin()
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showError(message: String)
        fun showLoading()
        fun hideLoading()
    }
}