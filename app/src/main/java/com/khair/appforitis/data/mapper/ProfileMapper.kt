package com.khair.appforitis.data.mapper

import com.khair.appforitis.data.model.NetworkCompanyItemDto
import com.khair.appforitis.data.model.NetworkProfile
import com.khair.appforitis.domain.entity.CompanyItem
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.domain.mapper.Mapper

class ProfileMapper: Mapper<NetworkProfile, Profile> {

    override fun map(from: NetworkProfile): Profile {
        return Profile(
            from.id,
            from.name,
            CompanyItem(from.company?.id ?: -1, from.company?.name ?: ""),
            from.phone,
            from.vk ?: "",
            from.telegram ?: "",
            from.facebook ?: "",
            from.additionalInformation ?: ""
        )
    }

    override fun reverseMap(to: Profile): NetworkProfile {
        return NetworkProfile(
            to.id,
            to.name,
            NetworkCompanyItemDto(to.company.id, to.company.name),
            to.phone,
            to.vk,
            to.telegram,
            to.facebook,
            to.additionalInformation
        )
    }
}