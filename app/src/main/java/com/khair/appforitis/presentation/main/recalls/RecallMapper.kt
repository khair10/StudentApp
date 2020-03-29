package com.khair.appforitis.presentation.main.recalls

import com.khair.appforitis.domain.entity.Recall
import com.khair.appforitis.domain.mapper.OneWayMapper
import com.khair.appforitis.presentation.main.recalls.dto.RecallPreviewDto

class RecallMapper:
    OneWayMapper<Recall, RecallPreviewDto> {

    override fun map(from: Recall): RecallPreviewDto {
        return RecallPreviewDto(
            from.id,
            from.student.name,
            from.company.name,
            from.rating,
            from.date
        )
    }
}