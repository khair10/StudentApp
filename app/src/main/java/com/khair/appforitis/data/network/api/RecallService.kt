package com.khair.appforitis.data.network.api

import com.khair.appforitis.data.model.NetworkRecall
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*

interface RecallService {

    @GET("/reviews")
    fun getRecalls(@Header("auth") jsonToken: String): Flowable<List<NetworkRecall>>

    @GET("/reviews/{query}")
    fun getRecallsWithQuery(@Path("query") query: String, @Header("auth") jsonToken: String): Flowable<List<NetworkRecall>>

    @GET("/reviews/review/{review_id}")
    fun getRecall(@Path("review_id") reviewId: Long, @Header("auth") jsonToken: String): Flowable<NetworkRecall>

    @POST("/reviews/review")
    fun postRecall(@Body recall: NetworkRecall, @Header("auth") jsonToken: String): Completable
}