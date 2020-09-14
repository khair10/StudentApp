package com.khair.appforitis.presentation.recall

import com.khair.appforitis.data.repositoryimpl.RecallRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListRecallRepository
import com.khair.appforitis.domain.entity.Recall
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class RecallPresenter(val id: Long): MvpPresenter<RecallContract.View>(), RecallContract.Presenter {

//    private val recallRepository: Repository<Recall> =
//        ArrayListRecallRepository()
private val recallRepository: Repository<Recall> =
    RecallRepository()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getRecall(id)
    }

    override fun getRecall(id: Long) {
        recallRepository.get(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .doOnTerminate{ viewState.hideLoading() }
            .subscribe(
                {recall -> viewState.showRecall(recall)},
                {exception -> when(exception){
                    is IllegalAccessException -> viewState.openLoginPage()
                    else -> viewState.showError(exception.message ?: unknownException)
                }}
            )
    }
}