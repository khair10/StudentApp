package com.khair.appforitis.data.model

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class NetworkProfile(
    @PrimaryKey
    var id: Long = 0,
    var name: String = "",
    @Ignore
    var company: NetworkCompanyItemDto = NetworkCompanyItemDto(),
    var phone: String = "",
    var vk: String = "",
    var telegram: String = "",
    var facebook: String = "",
    var additionalInformation: String = ""
): RealmObject()