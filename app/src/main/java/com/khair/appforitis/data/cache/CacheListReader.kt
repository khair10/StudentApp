package com.khair.appforitis.data.cache

import io.reactivex.Flowable
import io.reactivex.functions.Function
import io.realm.Realm
import io.realm.RealmObject

class CacheListReader<T: RealmObject>(val mClass: Class<T>): Function<Throwable, Flowable<List<T>>> {
    override fun apply(t: Throwable): Flowable<List<T>>? {
        val realm = Realm.getDefaultInstance()
        val results = realm.where(mClass).findAll()
        return Flowable.just(realm.copyFromRealm(results))
    }
}