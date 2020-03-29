package com.khair.appforitis.domain.mapper

interface OneWayMapper<From, To> {

    fun map(from: From): To
}