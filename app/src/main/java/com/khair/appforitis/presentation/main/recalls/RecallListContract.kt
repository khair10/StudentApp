package com.khair.appforitis.presentation.main.recalls

import com.khair.appforitis.presentation.main.recalls.dto.RecallPreviewDto

interface RecallListContract {

    interface Presenter {

        fun getRecalls()
        fun getRecalls(query: String?)
        fun getRecallsSorted(sortOption: SortOption)
    }

    interface View {

        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun showRecalls(recalls: List<RecallPreviewDto>)
        fun showEmpty()
        fun openLoginPage()
    }
}