package com.khair.appforitis.presentation.main.vacancies.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khair.appforitis.R
import com.khair.appforitis.presentation.base.Navigation
import com.khair.appforitis.presentation.main.vacancies.dto.VacancyPreviewDto

class VacancyListAdapter(var navigation: Navigation): RecyclerView.Adapter<VacancyViewHolder>() {

    var vacancyPreviewDtos: List<VacancyPreviewDto> = ArrayList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vacancy_list, parent, false)
        return VacancyViewHolder(
            view,
            navigation
        )
    }

    override fun getItemCount(): Int {
        return vacancyPreviewDtos.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancyPreviewDtos[position])
    }
}