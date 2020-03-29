package com.khair.appforitis.presentation.profile

import com.khair.appforitis.domain.entity.Profile

interface ProfileContract {

    interface Presenter{

        fun getProfile(id: Long)
    }

    interface View{

        fun showProfile(profile: Profile)
        fun showLoading()
        fun hideLoading()
        fun showException(message: String)
        fun openLoginPage()
    }
}