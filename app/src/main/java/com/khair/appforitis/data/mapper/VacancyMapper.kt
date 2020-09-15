package com.khair.appforitis.data.mapper

import com.khair.appforitis.data.model.NetworkCompanyItemDto
import com.khair.appforitis.data.model.NetworkStudentItemDto
import com.khair.appforitis.data.model.NetworkVacancy
import com.khair.appforitis.domain.entity.CompanyItem
import com.khair.appforitis.domain.entity.StudentItem
import com.khair.appforitis.domain.entity.Vacancy
import com.khair.appforitis.domain.mapper.Mapper
import java.util.*

class VacancyMapper: Mapper<NetworkVacancy, Vacancy> {

    override fun map(from: NetworkVacancy): Vacancy {
        return Vacancy(
            from.id,
            from.name,
            from.information,
            CompanyItem(from.company?.id ?: -1, from.company?.name ?: ""),
            from.rating,
            from.recallsCount,
            from.salary,
            StudentItem(from.student?.id ?: -1, from.student?.name ?: ""),
            Date(from.date)
        )
    }

    override fun reverseMap(to: Vacancy): NetworkVacancy {
        return NetworkVacancy(
            to.id,
            to.name,
            to.information,
            NetworkCompanyItemDto(to.company.id, to.company.name),
            to.rating,
            to.recallsCount,
            to.salary,
            NetworkStudentItemDto(to.student.id, to.student.name),
            to.date.time
        )
    }
}