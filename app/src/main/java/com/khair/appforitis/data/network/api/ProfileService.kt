package com.khair.appforitis.data.network.api

import com.khair.appforitis.data.model.NetworkProfile
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*

interface ProfileService {

    @GET("/users")
    fun getProfiles(@Header("auth") jsonToken: String): Flowable<List<NetworkProfile>>

    @GET("/users/user")
    fun getMyProfile(@Header("auth") jsonToken: String): Flowable<NetworkProfile>

    @GET("/users/user/{user_id}")
    fun getProfile(@Path("user_id") userId: Long, @Header("auth") jsonToken: String): Flowable<NetworkProfile>

    @POST("/users/user")
    fun editProfile(@Body networkProfile: NetworkProfile, @Header("auth") jsonToken: String): Completable
}