package com.khair.appforitis.data.repositoryimpl

import com.khair.appforitis.data.mapper.RegistrationMapper
import com.khair.appforitis.data.model.NetworkRegistrationForm
import com.khair.appforitis.data.network.ApiFactory
import com.khair.appforitis.domain.entity.RegistrationForm
import com.khair.appforitis.domain.repository.RegistrationRepository
import com.khair.appforitis.domain.mapper.OneWayMapper
import io.reactivex.Completable

class RegistrationRepositoryImpl: RegistrationRepository<RegistrationForm> {

    private val apiFactory: ApiFactory = ApiFactory
    private val registrationMapper: OneWayMapper<RegistrationForm, NetworkRegistrationForm> = RegistrationMapper()

    override fun registrate(registrationForm: RegistrationForm): Completable {
        val networkRegistrationForm = registrationMapper.map(registrationForm)
        return apiFactory.authService.registrate(networkRegistrationForm)
    }
}