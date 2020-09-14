package com.khair.appforitis.presentation.main.companies.util

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khair.appforitis.R
import com.khair.appforitis.presentation.base.Navigation
import com.khair.appforitis.presentation.main.companies.dto.CompanyPreviewDto

class CompanyViewHolder(itemView: View, val navigation: Navigation) : RecyclerView.ViewHolder(itemView) {

    private val rbRecallsOverall: RatingBar
    private val tvCompanyName: TextView
    private val tvRecallsCount: TextView

    init {
        rbRecallsOverall = itemView.findViewById(R.id.rb_company_rating)
        tvCompanyName = itemView.findViewById(R.id.tv_company_name)
        tvRecallsCount = itemView.findViewById(R.id.tv_recalls_count)
    }

    fun bind(companyPreviewDto: CompanyPreviewDto) {
        companyPreviewDto.run {
            rbRecallsOverall.rating = rating
            tvCompanyName.text = name
            tvRecallsCount.text = recallsCount
        }
        itemView.setOnClickListener {
            navigation.navigate(companyPreviewDto.id)
        }
    }
}