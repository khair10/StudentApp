package com.khair.appforitis.data.mapper

import com.khair.appforitis.data.model.NetworkCompany
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.mapper.Mapper

class CompanyMapper: Mapper<NetworkCompany, Company> {

    override fun map(from: NetworkCompany): Company {
        return Company(
            from.id,
            from.name,
            from.address,
            from.website,
            from.phone,
            from.information,
            from.rating,
            from.recallsCount
        )
    }

    override fun reverseMap(to: Company): NetworkCompany {
        return NetworkCompany(
            to.id,
            to.name,
            to.address,
            to.website,
            to.phone,
            to.information,
            to.rating,
            to.recallsCount
        )
    }
}