package com.khair.appforitis.data.repositoryimpl

import com.khair.appforitis.data.mapper.LoginMapper
import com.khair.appforitis.data.model.NetworkLoginForm
import com.khair.appforitis.data.network.ApiFactory
import com.khair.appforitis.domain.entity.Authentication
import com.khair.appforitis.domain.entity.LoginForm
import com.khair.appforitis.domain.mapper.OneWayMapper
import com.khair.appforitis.domain.repository.AuthRepository
import io.reactivex.Flowable

class LoginRepository: AuthRepository<LoginForm> {

    private val apiFactory: ApiFactory = ApiFactory
    private val loginMapper: OneWayMapper<LoginForm, NetworkLoginForm> = LoginMapper()
    private val exception = IllegalAccessException("Ошибка авторизации")

    override fun login(loginForm: LoginForm): Flowable<Authentication> {
        val networkLoginForm = loginMapper.map(loginForm)
        return apiFactory.authService.login(networkLoginForm)
            .map { item -> Authentication(item.studentItemDto.studentId, item.studentItemDto.name, item.jwtToken, item.refreshToken) }
    }
}