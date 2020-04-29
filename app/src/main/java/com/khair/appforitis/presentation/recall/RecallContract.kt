package com.khair.appforitis.presentation.recall

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.domain.entity.Recall

interface RecallContract {

    interface Presenter{

        fun getRecall(id: Long)
    }

    interface View: MvpView{

        fun showRecall(recall: Recall)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showError(message: String)
        fun openLoginPage()
    }
}