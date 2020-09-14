package com.khair.appforitis.presentation.recall

import com.khair.appforitis.domain.entity.Recall
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface RecallContract {

    interface Presenter{

        fun getRecall(id: Long)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun showRecall(recall: Recall)
        fun showLoading()
        fun hideLoading()
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(message: String)
        fun openLoginPage()
    }
}