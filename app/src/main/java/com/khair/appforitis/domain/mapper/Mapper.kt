package com.khair.appforitis.domain.mapper

interface Mapper<From, To> {

    fun map(from: From): To
    fun reverseMap(to: To): From
}