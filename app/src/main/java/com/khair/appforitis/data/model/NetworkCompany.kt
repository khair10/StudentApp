package com.khair.appforitis.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NetworkCompany(
    @PrimaryKey
    var id: Long = 0,
    var name: String = "",
    var address: String = "",
    var website: String = "",
    var phone: String = "",
    var information: String = "",
    var rating: Float = 0F,
    var recallsCount: Int = 0
): RealmObject()