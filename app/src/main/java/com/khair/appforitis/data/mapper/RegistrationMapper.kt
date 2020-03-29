package com.khair.appforitis.data.mapper

import com.khair.appforitis.data.model.NetworkLoginForm
import com.khair.appforitis.data.model.NetworkProfileItem
import com.khair.appforitis.data.model.NetworkRegistrationForm
import com.khair.appforitis.domain.entity.RegistrationForm
import com.khair.appforitis.domain.mapper.OneWayMapper

class RegistrationMapper: OneWayMapper<RegistrationForm, NetworkRegistrationForm> {

    override fun map(from: RegistrationForm): NetworkRegistrationForm {
        return NetworkRegistrationForm(
            from.login,
            from.password,
            from.passwordConfirm,
            NetworkProfileItem(
                from.profile.name,
                from.profile.phone
            )
        )
    }
}