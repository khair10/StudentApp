package com.khair.appforitis.data.network.api

import com.khair.appforitis.data.model.NetworkProfile
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*

interface ProfileService {

    @GET("/users/list")
    fun getProfiles(): Flowable<List<NetworkProfile>>

    @GET("/users/user")
    fun getMyProfile(): Flowable<NetworkProfile>

    @GET("/users/user/{user_id}")
    fun getProfile(@Path("user_id") userId: Long): Flowable<NetworkProfile>

    @POST("/users/user")
    fun editProfile(@Body networkProfile: NetworkProfile): Completable
}