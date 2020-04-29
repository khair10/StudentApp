package com.khair.appforitis.data.network.api

import com.khair.appforitis.data.model.NetworkCompany
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.http.*

interface CompanyService {

    @GET("/companies/list")
    fun getCompanies(): Flowable<List<NetworkCompany>>

    @GET("/companies/list/{query}")
    fun getCompaniesWithQuery(@Path("query") query: String): Flowable<List<NetworkCompany>>

    @GET("/companies/company/{companyId}")
    fun getCompany(@Path("companyId") companyId: Long): Flowable<NetworkCompany>

    @POST("/companies/company")
    fun postCompany(@Body company: NetworkCompany): Completable
}