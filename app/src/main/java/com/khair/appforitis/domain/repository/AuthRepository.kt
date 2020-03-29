package com.khair.appforitis.domain.repository

import com.khair.appforitis.domain.entity.Authentication
import io.reactivex.Completable
import io.reactivex.Flowable

interface AuthRepository<T> {

    fun login(loginForm: T): Flowable<Authentication>
}