package com.khair.appforitis.presentation.main.recalls

import com.khair.appforitis.data.repositoryimpl.RecallRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListRecallRepository
import com.khair.appforitis.domain.entity.Recall
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.domain.mapper.OneWayMapper
import com.khair.appforitis.presentation.main.recalls.dto.RecallPreviewDto
import com.khair.appforitis.unknownException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class RecallListPresenter(): MvpPresenter<RecallListContract.View>(), RecallListContract.Presenter {

//    private val recallRepository: Repository<Recall> =
//        ArrayListRecallRepository()
private val recallRepository: Repository<Recall> =
    RecallRepository()
    private val recallMapper: OneWayMapper<Recall, RecallPreviewDto> = RecallMapper()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getRecalls()
    }

    override fun getRecalls() {
        recallRepository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .map { recallMapper.map(it) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { recalls -> checkAndShow(recalls) },
                { exception -> when(exception){
                    is IllegalAccessException -> viewState.openLoginPage()
                    else -> viewState.showError(exception.message ?: unknownException)
                } }
            )
    }

    override fun getRecalls(query: String?) {
        if(query == null){
            return
        }
        recallRepository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .filter {
                val lowerQuery = query.toLowerCase()
                it.company.name.toLowerCase().startsWith(lowerQuery) ||
                        it.student.name.toLowerCase().startsWith(lowerQuery)
            }
            .map { recallMapper.map(it) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { recalls -> checkAndShow(recalls) },
                { exception -> viewState.showError(exception.message ?: unknownException) }
            )
    }

    override fun getRecallsSorted(sortOption: SortOption) {
        when(sortOption){
            SortOption.ALPHABET -> {
                recallRepository.getAll()
                    .concatMap { Flowable.fromIterable(it) }
                    .sorted { t, t2 -> t2.company.name.compareTo(t.company.name) } //TODO sorted
                    .map { recallMapper.map(it) }
                    .toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { viewState.showLoading() }
                    .doOnTerminate { viewState.hideLoading() }
                    .subscribe(
                        { recalls -> checkAndShow(recalls) },
                        { exception -> viewState.showError(exception.message ?: unknownException) }
                    )
            }
            SortOption.DATE -> {
                recallRepository.getAll()
                    .concatMap { Flowable.fromIterable(it) }
                    .sorted { r1, r2 -> r1.date.compareTo(r2.date)} //TODO sorted наоборот по дате
                    .map { recallMapper.map(it) }
                    .toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { viewState.showLoading() }
                    .doOnTerminate { viewState.hideLoading() }
                    .subscribe(
                        { recalls -> checkAndShow(recalls) },
                        { exception -> viewState.showError(exception.message ?: unknownException) }
                    )
            }
        }
    }

    private fun checkAndShow(recalls: List<RecallPreviewDto>){
        if(recalls.isEmpty())
            viewState.showEmpty()
        else
            viewState.showRecalls(recalls)
    }
}