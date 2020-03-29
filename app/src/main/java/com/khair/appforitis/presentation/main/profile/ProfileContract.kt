package com.khair.appforitis.presentation.main.profile

import com.khair.appforitis.domain.entity.Profile

interface ProfileContract {

    interface Presenter{

        fun getProfile()
    }

    interface View{

        fun showProfile(profile: Profile)
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun openLoginPage()
    }
}