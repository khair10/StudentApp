package com.khair.appforitis.presentation.recall

import com.khair.appforitis.domain.entity.Recall

interface RecallContract {

    interface Presenter{

        fun getRecall(id: Long)
    }

    interface View{

        fun showRecall(recall: Recall)
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun openLoginPage()
    }
}