package com.khair.appforitis.data.network

import com.khair.appforitis.data.network.RetrofitHelper
import com.khair.appforitis.data.network.api.*

object ApiFactory {

    val companyService: CompanyService by lazy {
        RetrofitHelper.retrofit.create(CompanyService::class.java)
    }

    val profileService: ProfileService by lazy {
        RetrofitHelper.retrofit.create(ProfileService::class.java)
    }

    val recallService: RecallService by lazy {
        RetrofitHelper.retrofit.create(RecallService::class.java)
    }

    val vacancyService: VacancyService by lazy {
        RetrofitHelper.retrofit.create(VacancyService::class.java)
    }

    val authService: AuthService by lazy {
        RetrofitHelper.retrofit.create(AuthService::class.java)
    }

//    fun createCompanyService() = RetrofitHelper.retrofit.create(CompanyService::class.java)
//    fun createProfileService() = RetrofitHelper.retrofit.create(ProfileService::class.java)
//    fun createRecallService() = RetrofitHelper.retrofit.create(RecallService::class.java)
//    fun createVacancyService() = RetrofitHelper.retrofit.create(VacancyService::class.java)
}