package com.khair.appforitis.domain.presenter

import com.khair.appforitis.domain.entity.Company

interface PresenterProtocol<T> {

    fun showCompany(item: T)
}