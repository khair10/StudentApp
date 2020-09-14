package com.khair.appforitis.data.cache

import io.reactivex.Flowable
import io.reactivex.functions.Function
import io.realm.Realm
import io.realm.RealmObject

class CacheSingleWriter<T: RealmObject>(val mClass: Class<T>):
    Function<T, Flowable<T>> {
    override fun apply(element: T): Flowable<T> {
        Realm.getDefaultInstance().executeTransaction {
                realm -> run {
            realm.delete(mClass)
            realm.insert(element)
        }
        }
        return Flowable.just(element)
    }
}