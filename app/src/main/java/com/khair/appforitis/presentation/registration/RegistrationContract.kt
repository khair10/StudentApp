package com.khair.appforitis.presentation.registration

import com.khair.appforitis.presentation.registration.dto.RegistrationDto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface RegistrationContract {

    interface Presenter{

        fun registration(registrationForm: RegistrationDto)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun openLogin()
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(message: String)
        fun showLoading()
        fun hideLoading()
    }
}