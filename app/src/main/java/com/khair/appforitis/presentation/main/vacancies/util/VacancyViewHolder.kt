package com.khair.appforitis.presentation.main.vacancies.util

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khair.appforitis.R
import com.khair.appforitis.presentation.base.Navigation
import com.khair.appforitis.presentation.main.vacancies.dto.VacancyPreviewDto
import java.text.SimpleDateFormat

class VacancyViewHolder(itemView: View, var navigation: Navigation) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private val dateTimeFormatter = SimpleDateFormat("dd.MM.yyyy")
    }

    private val tvTitle: TextView
    private val tvCompanyName: TextView
    private val tvPreview: TextView
    private val tvDate: TextView

    init {
        itemView.run {
            tvTitle = findViewById(R.id.tv_vacancy_name)
            tvCompanyName = findViewById(R.id.tv_company_name)
            tvPreview = findViewById(R.id.tv_vacancy_preview)
            tvDate = findViewById(R.id.tv_vacancy_date)
        }
    }

    fun bind(item: VacancyPreviewDto){
        item.run {
            tvTitle.text = title
            tvCompanyName.text = companyName
            tvPreview.text = preview
            tvDate.text = dateTimeFormatter.format(date)
        }
        itemView.setOnClickListener {
            navigation.navigate(item.id)
        }
    }
}