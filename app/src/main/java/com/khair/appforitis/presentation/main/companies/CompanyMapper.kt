package com.khair.appforitis.presentation.main.companies

import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.mapper.OneWayMapper
import com.khair.appforitis.presentation.main.companies.dto.CompanyPreviewDto

class CompanyMapper:
    OneWayMapper<Company, CompanyPreviewDto> {

    override fun map(from: Company): CompanyPreviewDto {
        return CompanyPreviewDto(
            from.id,
            from.name,
            from.rating,
            from.recallsCount
        )
    }
}