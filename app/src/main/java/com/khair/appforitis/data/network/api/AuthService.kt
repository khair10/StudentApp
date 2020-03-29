package com.khair.appforitis.data.network.api

import com.khair.appforitis.data.model.NetworkLoginForm
import com.khair.appforitis.data.model.NetworkRegistrationForm
import com.khair.appforitis.domain.entity.Authentication
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.POST

interface AuthService {

    @POST("/login")
    fun login(loginForm: NetworkLoginForm): Flowable<Authentication>

    @POST("/registrate")
    fun registrate(registrationForm: NetworkRegistrationForm): Completable
}