package com.khair.appforitis.data.repositoryimpl

import com.khair.appforitis.data.cache.CacheListReader
import com.khair.appforitis.data.cache.CacheSingleReader
import com.khair.appforitis.data.cache.CacheSingleWriter
import com.khair.appforitis.data.cache.CacheWriter
import com.khair.appforitis.data.mapper.ProfileMapper
import com.khair.appforitis.data.model.NetworkProfile
import com.khair.appforitis.data.model.NetworkProfileItem
import com.khair.appforitis.data.network.ApiFactory
import com.khair.appforitis.data.network.AuthenticationProvider
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.domain.repository.Repository
import io.reactivex.Completable
import io.reactivex.Flowable

class ProfileRepository: Repository<Profile> {

    private val apiFactory: ApiFactory = ApiFactory
    private val profileMapper: ProfileMapper = ProfileMapper()
    private val exception = IllegalAccessException("Вы не авторизованы")

    override fun get(id: Long): Flowable<Profile> {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
            return apiFactory.profileService.getProfile(id)
                .map { profileMapper.map(it) }
//        }
//        return Flowable.error<Profile>(exception)
    }

    override fun getAll(): Flowable<List<Profile>> {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
            return apiFactory.profileService.getProfiles()
                .flatMap (CacheWriter(NetworkProfile::class.java))
                .onErrorResumeNext(CacheListReader(NetworkProfile::class.java))
                .concatMap { Flowable.fromIterable(it) }
                .map { profileMapper.map(it) }
                .toList()
                .toFlowable()
//        }
//        return Flowable.error<List<Profile>>(exception)
    }

    override fun add(item: Profile): Completable {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
            val networkProfile = profileMapper.reverseMap(item)
            return apiFactory.profileService.editProfile(networkProfile)
//        }
//        return Completable.error(exception)
    }

    override fun get(): Flowable<Profile> {
//        if(AuthenticationProvider.isAuthenticated()) {
//            val authentication = AuthenticationProvider.fetchAuthentication()
        //TODO getMyProfile()
            return apiFactory.profileService.getProfile(AuthenticationProvider.fetchAuthentication().id)
                .flatMap (CacheSingleWriter(NetworkProfile::class.java))
                .onErrorResumeNext(CacheSingleReader(AuthenticationProvider.fetchAuthentication().id, NetworkProfile::class.java))
                .map { profileMapper.map(it) }
//        }
//        return Flowable.error<Profile>(exception)
    }
}