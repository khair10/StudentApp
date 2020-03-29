package com.khair.appforitis.data.mapper

import com.khair.appforitis.data.model.NetworkCompanyItemDto
import com.khair.appforitis.data.model.NetworkRecall
import com.khair.appforitis.data.model.NetworkStudentItemDto
import com.khair.appforitis.domain.entity.CompanyItem
import com.khair.appforitis.domain.entity.Recall
import com.khair.appforitis.domain.entity.StudentItem
import com.khair.appforitis.domain.mapper.Mapper

class RecallMapper: Mapper<NetworkRecall, Recall> {

    override fun map(from: NetworkRecall): Recall {
        return Recall(
            from.id,
            StudentItem(from.student.id, from.student.name),
            from.information,
            CompanyItem(from.company.id, from.company.name),
            from.rating,
            from.date
        )
    }

    override fun reverseMap(to: Recall): NetworkRecall {
        return NetworkRecall(
            to.id,
            NetworkStudentItemDto(to.student.id, to.student.name),
            to.information,
            NetworkCompanyItemDto(to.company.id, to.company.name),
            to.rating,
            to.date
        )
    }
}