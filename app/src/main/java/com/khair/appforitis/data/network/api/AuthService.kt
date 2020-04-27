package com.khair.appforitis.data.network.api

import com.khair.appforitis.data.model.NetworkAuthentication
import com.khair.appforitis.data.model.NetworkLoginForm
import com.khair.appforitis.data.model.NetworkRegistrationForm
import com.khair.appforitis.domain.entity.Authentication
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/login")
    fun login(@Body loginForm: NetworkLoginForm): Flowable<NetworkAuthentication>

    @POST("/registrate")
    fun registrate(@Body registrationForm: NetworkRegistrationForm): Completable
}