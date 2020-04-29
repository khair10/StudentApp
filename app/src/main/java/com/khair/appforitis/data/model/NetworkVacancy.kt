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
    @Ignore
    var company: NetworkCompanyItemDto = NetworkCompanyItemDto(),
    var rating: Float = 0F,
    var recallsCount: Int = 0,
    var salary: Int = 0,
    @Ignore
    var student: NetworkStudentItemDto = NetworkStudentItemDto(),
    var date: Long = 0
): RealmObject()