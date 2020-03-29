package com.khair.appforitis.presentation.main.companies.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khair.appforitis.R
import com.khair.appforitis.presentation.base.Navigation
import com.khair.appforitis.presentation.main.companies.dto.CompanyPreviewDto

class CompanyListAdapter(var navigation: Navigation): RecyclerView.Adapter<CompanyViewHolder>() {

    var companyPreviewDtos: List<CompanyPreviewDto> = ArrayList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_company_list, parent, false)
        return CompanyViewHolder(
            view,
            navigation
        )
    }

    override fun getItemCount(): Int {
        return companyPreviewDtos.size
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(companyPreviewDtos[position])
    }
}