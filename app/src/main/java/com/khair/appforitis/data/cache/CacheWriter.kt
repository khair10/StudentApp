package com.khair.appforitis.data.cache

import io.reactivex.Flowable
import io.reactivex.functions.Function
import io.realm.Realm
import io.realm.RealmObject

class CacheWriter<T: RealmObject>(val mClass: Class<T>): Function<List<T>, Flowable<List<T>>> {
    override fun apply(elements: List<T>): Flowable<List<T>> {
        Realm.getDefaultInstance().executeTransaction {
            realm -> run {
                realm.delete(mClass)
                realm.insert(elements)
            }
        }
        return Flowable.just(elements)
    }
}