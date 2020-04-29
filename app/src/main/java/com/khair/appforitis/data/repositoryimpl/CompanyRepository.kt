package com.khair.appforitis.data.repositoryimpl

import com.khair.appforitis.data.cache.CacheListReader
import com.khair.appforitis.data.cache.CacheSingleReader
import com.khair.appforitis.data.cache.CacheWriter
import com.khair.appforitis.data.mapper.CompanyMapper
import com.khair.appforitis.data.model.NetworkCompany
import com.khair.appforitis.data.network.ApiFactory
import com.khair.appforitis.data.network.AuthenticationProvider
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.repository.Repository
import io.reactivex.Completable
import io.reactivex.Flowable

class CompanyRepository: Repository<Company> {

    private val apiFactory: ApiFactory = ApiFactory
    private val companyMapper: CompanyMapper = CompanyMapper()
    private val exception = IllegalAccessException("Вы не авторизованы")

    override fun get(id: Long): Flowable<Company> {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
            return apiFactory.companyService.getCompany(id) //, authentication.jsonToken)
                .onErrorResumeNext(CacheSingleReader(id, NetworkCompany::class.java))
                .map { companyMapper.map(it) }
//        }
//        return Flowable.error<Company>(exception)
    }

    override fun getAll(): Flowable<List<Company>> {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
            return apiFactory.companyService.getCompanies() //, authentication.jsonToken)
                .flatMap(CacheWriter(NetworkCompany::class.java))
                .onErrorResumeNext(CacheListReader(NetworkCompany::class.java))
                .concatMap { Flowable.fromIterable(it) }
                .map { companyMapper.map(it) }
                .toList()
                .toFlowable()
//        }
//        return Flowable.error<List<Company>>(exception)
    }

    override fun add(item: Company): Completable {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
            val networkCompany = companyMapper.reverseMap(item)
            return apiFactory.companyService.postCompany(networkCompany) // , authentication.jsonToken)
//        }
//        return Completable.error(exception)
    }

    override fun get(): Flowable<Company> {
//        if(AuthenticationProvider.isAuthenticated()) {
            return Flowable.empty<Company>()
//        }
//        return Flowable.error<Company>(exception)
    }
}