package com.khair.appforitis.data.model

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class NetworkRecall(
    @PrimaryKey
    var id: Long = 0,
    var student: NetworkStudentItemDto? = null,
    var information: String = "",
    var company: NetworkCompanyItemDto? = null,
    var rating: Float = 0F,
    var date: Long = 0
): RealmObject()