package com.khair.appforitis.presentation.main.recalls.util

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khair.appforitis.R
import com.khair.appforitis.presentation.base.Navigation
import com.khair.appforitis.presentation.main.recalls.dto.RecallPreviewDto
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class RecallViewHolder(itemView: View, var navigation: Navigation): RecyclerView.ViewHolder(itemView) {

    companion object {
        private val dateTimeFormatter = SimpleDateFormat("dd.MM.yyyy")
    }

    private val rbOverall: RatingBar
    private val tvCompanyName: TextView
    private val tvStudentName: TextView
    private val tvDate: TextView

    init {
        rbOverall = itemView.findViewById(R.id.rb_company_rating)
        tvCompanyName = itemView.findViewById(R.id.tv_company_name)
        tvStudentName = itemView.findViewById(R.id.tv_student_name)
        tvDate = itemView.findViewById(R.id.tv_date)
    }

    fun bind(recallPreviewDto: RecallPreviewDto) {
        recallPreviewDto.run {
            rbOverall.rating = rating
            tvCompanyName.text = companyName
            tvStudentName.text = studentName
            tvDate.text = dateTimeFormatter.format(date)
        }
        itemView.setOnClickListener {
            navigation.navigate(recallPreviewDto.id)
        }
    }
}