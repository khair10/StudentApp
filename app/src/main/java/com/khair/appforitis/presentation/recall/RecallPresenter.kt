package com.khair.appforitis.presentation.recall

import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListRecallRepository
import com.khair.appforitis.domain.entity.Recall
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RecallPresenter(var view: RecallContract.View): RecallContract.Presenter {

    private val recallRepository: Repository<Recall> =
        ArrayListRecallRepository()

    override fun getRecall(id: Long) {
        recallRepository.get(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate{ view.hideLoading() }
            .subscribe(
                {recall -> view.showRecall(recall)},
                {exception -> when(exception){
                    is IllegalAccessException -> view.openLoginPage()
                    else -> view.showError(exception.message ?: unknownException)
                }}
            )
    }
}