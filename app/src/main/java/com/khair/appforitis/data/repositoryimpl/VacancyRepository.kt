package com.khair.appforitis.data.repositoryimpl

import com.khair.appforitis.data.cache.CacheListReader
import com.khair.appforitis.data.cache.CacheSingleReader
import com.khair.appforitis.data.cache.CacheWriter
import com.khair.appforitis.data.mapper.VacancyMapper
import com.khair.appforitis.data.model.NetworkVacancy
import com.khair.appforitis.data.network.ApiFactory
import com.khair.appforitis.data.network.AuthenticationProvider
import com.khair.appforitis.domain.entity.Recall
import com.khair.appforitis.domain.entity.Vacancy
import com.khair.appforitis.domain.repository.Repository
import io.reactivex.Completable
import io.reactivex.Flowable

class VacancyRepository: Repository<Vacancy> {

    private val apiFactory: ApiFactory = ApiFactory
    private val vacancyMapper: VacancyMapper = VacancyMapper()
    private val exception = IllegalAccessException("Вы не авторизованы")

    override fun get(id: Long): Flowable<Vacancy> {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
            return apiFactory.vacancyService.getVacancy(id)
                .onErrorResumeNext(CacheSingleReader(id, NetworkVacancy::class.java))
                .map { vacancyMapper.map(it) }
//        }
//        return Flowable.error<Vacancy>(exception)
    }

    override fun getAll(): Flowable<List<Vacancy>> {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
            return apiFactory.vacancyService.getVacancies()
                .flatMap (CacheWriter(NetworkVacancy::class.java))
                .onErrorResumeNext(CacheListReader(NetworkVacancy::class.java))
                .concatMap { Flowable.fromIterable(it) }
                .map { vacancyMapper.map(it) }
                .toList()
                .toFlowable()
//        }
//        return Flowable.error<List<Vacancy>>(exception)
    }

    override fun add(item: Vacancy): Completable {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
            val networkVacancy = vacancyMapper.reverseMap(item)
            return apiFactory.vacancyService.postVacancy(networkVacancy)
//        }
//        return Completable.error(exception)
    }

    override fun get(): Flowable<Vacancy> {
//        if(AuthenticationProvider.isAuthenticated()) {
            return Flowable.empty<Vacancy>()
//        }
//        return Flowable.error(exception)
    }
}