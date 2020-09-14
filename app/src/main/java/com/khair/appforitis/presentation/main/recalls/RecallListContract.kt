package com.khair.appforitis.presentation.main.recalls

import com.khair.appforitis.presentation.main.recalls.dto.RecallPreviewDto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface RecallListContract {

    interface Presenter {

        fun getRecalls()
        fun getRecalls(query: String?)
        fun getRecallsSorted(sortOption: SortOption)
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View: MvpView {

        fun showLoading()
        fun hideLoading()
        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(message: String)
        fun showRecalls(recalls: List<RecallPreviewDto>)
        fun showEmpty()
        fun openLoginPage()
    }
}