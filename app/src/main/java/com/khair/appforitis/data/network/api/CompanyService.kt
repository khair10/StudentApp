package com.khair.appforitis.data.network.api

import com.khair.appforitis.data.model.NetworkCompany
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*

interface CompanyService {

    @GET("/companies")
    fun getCompanies(@Header("auth") jsonToken: String): Flowable<List<NetworkCompany>>

    @GET("/companies/{query}")
    fun getCompaniesWithQuery(@Path("query") query: String, @Header("auth") jsonToken: String): Flowable<List<NetworkCompany>>

    @GET("/companies/company/{companyId}")
    fun getCompany(@Path("companyId") companyId: Long, @Header("auth") jsonToken: String): Flowable<NetworkCompany>

    @POST("/companies/company")
    fun postCompany(@Body company: NetworkCompany, @Header("auth") jsonToken: String): Completable
}