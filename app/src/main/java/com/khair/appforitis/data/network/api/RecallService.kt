package com.khair.appforitis.data.network.api

import com.khair.appforitis.data.model.NetworkRecall
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*

interface RecallService {

    @GET("/recalls/list")
    fun getRecalls(@Header("Authorization") jsonToken: String): Flowable<List<NetworkRecall>>

    @GET("/recalls/list/{query}")
    fun getRecallsWithQuery(@Path("query") query: String, @Header("Authorization") jsonToken: String): Flowable<List<NetworkRecall>>

    @GET("/recalls/recall/{review_id}")
    fun getRecall(@Path("review_id") reviewId: Long, @Header("Authorization") jsonToken: String): Flowable<NetworkRecall>

    @POST("/recalls/recall")
    fun postRecall(@Body recall: NetworkRecall, @Header("Authorization") jsonToken: String): Completable
}