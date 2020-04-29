package com.khair.appforitis.presentation.main.recalls

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khair.appforitis.presentation.main.recalls.dto.RecallPreviewDto

interface RecallListContract {

    interface Presenter {

        fun getRecalls()
        fun getRecalls(query: String?)
        fun getRecallsSorted(sortOption: SortOption)
    }

    interface View: MvpView {

        fun showLoading()
        fun hideLoading()
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun showError(message: String)
        fun showRecalls(recalls: List<RecallPreviewDto>)
        fun showEmpty()
        fun openLoginPage()
    }
}