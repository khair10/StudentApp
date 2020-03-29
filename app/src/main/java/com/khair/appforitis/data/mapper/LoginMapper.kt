package com.khair.appforitis.data.mapper

import com.khair.appforitis.data.model.NetworkLoginForm
import com.khair.appforitis.domain.entity.LoginForm
import com.khair.appforitis.domain.mapper.OneWayMapper

class LoginMapper:
    OneWayMapper<LoginForm, NetworkLoginForm> {

    override fun map(from: LoginForm): NetworkLoginForm {
        return NetworkLoginForm(
            from.login,
            from.password
        )
    }
}