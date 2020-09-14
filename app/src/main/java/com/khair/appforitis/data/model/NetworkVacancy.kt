package com.khair.appforitis.data.model

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.util.*

open class NetworkVacancy(
    @PrimaryKey
    var id: Long = 0,
    var name: String = "",
    var information: String = "",
    var company: NetworkCompanyItemDto? = null,
    var rating: Float = 0F,
    var recallsCount: Int = 0,
    var salary: Int = 0,
    var student: NetworkStudentItemDto? = null,
    var date: Long = 0
): RealmObject()