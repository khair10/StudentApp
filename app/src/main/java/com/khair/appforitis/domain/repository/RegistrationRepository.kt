package com.khair.appforitis.domain.repository

import io.reactivex.Completable

interface RegistrationRepository<T> {

    fun registrate(registrationForm: T): Completable
}