package com.khair.appforitis.presentation.main.vacancies

import com.khair.appforitis.domain.entity.Vacancy
import com.khair.appforitis.domain.mapper.OneWayMapper
import com.khair.appforitis.presentation.main.vacancies.dto.VacancyPreviewDto

class VacancyMapper:
    OneWayMapper<Vacancy, VacancyPreviewDto> {

    override fun map(from: Vacancy): VacancyPreviewDto {
        return VacancyPreviewDto(
            from.id,
            from.name,
            from.company.name,
            from.information.substring(0, 100.coerceAtMost(from.information.length)),
            from.date
        )
    }
}