package com.khair.appforitis.data.network.api

import com.khair.appforitis.data.model.NetworkVacancy
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*

interface VacancyService {

    @GET("/vacancies")
    fun getVacancies(@Header("auth") jsonToken: String): Flowable<List<NetworkVacancy>>

    @GET("/vacancies/vacancy/{vacancy_id}")
    fun getVacancy(@Path("vacancy_id") vacancyId: Long, @Header("auth") jsonToken: String): Flowable<NetworkVacancy>

    @POST("/vacancies/vacancy")
    fun postVacancy(@Body vacancy: NetworkVacancy, @Header("auth") jsonToken: String): Completable
}