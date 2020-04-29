package com.khair.appforitis.data.network.api

import com.khair.appforitis.data.model.NetworkVacancy
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*

interface VacancyService {

    @GET("/vacancy/list")
    fun getVacancies(): Flowable<List<NetworkVacancy>>

    @GET("/vacancy/vacancy/{vacancy_id}")
    fun getVacancy(@Path("vacancy_id") vacancyId: Long): Flowable<NetworkVacancy>

    @POST("/vacancy/vacancy")
    fun postVacancy(@Body vacancy: NetworkVacancy): Completable
}