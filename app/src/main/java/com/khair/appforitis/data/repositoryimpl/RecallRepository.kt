package com.khair.appforitis.data.repositoryimpl

import com.khair.appforitis.data.cache.CacheListReader
import com.khair.appforitis.data.cache.CacheSingleReader
import com.khair.appforitis.data.cache.CacheWriter
import com.khair.appforitis.data.mapper.RecallMapper
import com.khair.appforitis.data.model.NetworkRecall
import com.khair.appforitis.data.network.ApiFactory
import com.khair.appforitis.data.network.AuthenticationProvider
import com.khair.appforitis.domain.entity.Recall
import com.khair.appforitis.domain.repository.Repository
import io.reactivex.Completable
import io.reactivex.Flowable

class RecallRepository: Repository<Recall> {

    private val apiFactory: ApiFactory = ApiFactory
    private val recallMapper: RecallMapper = RecallMapper()
    private val exception = IllegalAccessException("Вы не авторизованы")

    override fun get(id: Long): Flowable<Recall> {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
            return apiFactory.recallService.getRecall(id)
                .onErrorResumeNext(CacheSingleReader(id, NetworkRecall::class.java))
                .map { recallMapper.map(it) }
//        }
//        return Flowable.error<Recall>(exception)
    }

    override fun getAll(): Flowable<List<Recall>> {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
        return apiFactory.recallService.getRecalls()
            .flatMap(CacheWriter(NetworkRecall::class.java))
            .onErrorResumeNext(CacheListReader(NetworkRecall::class.java))
            .concatMap { Flowable.fromIterable(it) }
            .map { recallMapper.map(it) }
            .toList()
            .toFlowable()
//        }
//        return Flowable.error<List<Recall>>(exception)
    }

    override fun add(item: Recall): Completable {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
            val networkRecall = recallMapper.reverseMap(item)
            return apiFactory.recallService.postRecall(networkRecall)
//        }
//        return Completable.error(exception)
    }

    override fun get(): Flowable<Recall> {
//        if(AuthenticationProvider.isAuthenticated()) {
            return Flowable.empty<Recall>()
//        }
//        return Flowable.error(exception)
    }
}