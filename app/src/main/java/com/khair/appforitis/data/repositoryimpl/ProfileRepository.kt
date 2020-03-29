package com.khair.appforitis.data.repositoryimpl

import com.khair.appforitis.data.mapper.ProfileMapper
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
        if(AuthenticationProvider.isAuthenticated()) {
            val authentication = AuthenticationProvider.fetchAuthentication()
            return apiFactory.profileService.getProfile(id, authentication.jsonToken)
                .map { profileMapper.map(it) }
        }
        return Flowable.error<Profile>(exception)
    }

    override fun getAll(): Flowable<List<Profile>> {
        if(AuthenticationProvider.isAuthenticated()) {
            val authentication = AuthenticationProvider.fetchAuthentication()
            return apiFactory.profileService.getProfiles(authentication.jsonToken)
                .concatMap { Flowable.fromIterable(it) }
                .map { profileMapper.map(it) }
                .toList()
                .toFlowable()
        }
        return Flowable.error<List<Profile>>(exception)
    }

    override fun add(item: Profile): Completable {
        if(AuthenticationProvider.isAuthenticated()) {
            val authentication = AuthenticationProvider.fetchAuthentication()
            val networkProfile = profileMapper.reverseMap(item)
            return apiFactory.profileService.editProfile(networkProfile, authentication.jsonToken)
        }
        return Completable.error(exception)
    }

    override fun get(): Flowable<Profile> {
        if(AuthenticationProvider.isAuthenticated()) {
            val authentication = AuthenticationProvider.fetchAuthentication()
            return apiFactory.profileService.getProfile(authentication.id, authentication.jsonToken)
                .map { profileMapper.map(it) }
        }
        return Flowable.error<Profile>(exception)
    }
}