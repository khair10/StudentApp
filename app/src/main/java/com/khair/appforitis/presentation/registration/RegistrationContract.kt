package com.khair.appforitis.presentation.registration

import com.khair.appforitis.presentation.registration.dto.RegistrationDto

interface RegistrationContract {

    interface Presenter{

        fun registration(registrationForm: RegistrationDto)
    }

    interface View {

        fun openLogin()
        fun showError(message: String)
        fun showLoading()
        fun hideLoading()
    }
}