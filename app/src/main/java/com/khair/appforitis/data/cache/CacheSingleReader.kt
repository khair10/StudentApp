package com.khair.appforitis.data.cache

import io.reactivex.Flowable
import io.reactivex.functions.Function
import io.realm.Realm
import io.realm.RealmObject

class CacheSingleReader<T: RealmObject>(val id: Long, val mClass: Class<T>):
    Function<Throwable, Flowable<T>> {
    override fun apply(t: Throwable): Flowable<T>? {
        val realm = Realm.getDefaultInstance()
        val results = realm.where(mClass).equalTo("id", id).findAll()
        if(results.first() != null)
            return Flowable.just(realm.copyFromRealm(results.first()!!))
        else
            return Flowable.empty()
    }
}