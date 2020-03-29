package com.khair.appforitis.presentation.main.recalls.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khair.appforitis.R
import com.khair.appforitis.presentation.base.Navigation
import com.khair.appforitis.presentation.main.recalls.dto.RecallPreviewDto

class RecallListAdapter(var navigation: Navigation): RecyclerView.Adapter<RecallViewHolder>() {

    var recallPreviewDtos: List<RecallPreviewDto> = ArrayList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecallViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recall_list, parent, false)
        return RecallViewHolder(
            view,
            navigation
        )
    }

    override fun getItemCount(): Int {
        return recallPreviewDtos.size
    }

    override fun onBindViewHolder(holder: RecallViewHolder, position: Int) {
        holder.bind(recallPreviewDtos[position])
    }
}