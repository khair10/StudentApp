package com.khair.appforitis.data.model

import io.realm.RealmObject

open class NetworkCompanyItemDto(
    var id: Long = 0,
    var name: String = ""
): RealmObject()