package com.khair.appforitis.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NetworkStudentItemDto(
    var id: Long = 0,
    var name: String = ""
): RealmObject()