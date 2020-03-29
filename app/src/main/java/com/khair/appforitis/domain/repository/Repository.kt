package com.khair.appforitis.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable

interface Repository<T> {

    fun get(): Flowable<T>
    fun get(id: Long): Flowable<T>
    fun getAll(): Flowable<List<T>>
    fun add(item: T): Completable
}