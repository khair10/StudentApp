package com.khair.appforitis.presentation.recallsforcompany

import com.khair.appforitis.data.repositoryimpl.CompanyRepository
import com.khair.appforitis.data.repositoryimpl.RecallRepository
import com.khair.appforitis.domain.entity.Recall
import com.khair.appforitis.domain.mapper.OneWayMapper
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.presentation.main.recalls.RecallMapper
import com.khair.appforitis.presentation.main.recalls.dto.RecallPreviewDto
import com.khair.appforitis.unknownException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter

class CompanyRecallListPresenter(val company: String): MvpPresenter<CompanyRecallListContract.View>(),
CompanyRecallListContract.Presenter{

    private var repository: Repository<Recall> = RecallRepository()
    private val recallMapper: OneWayMapper<Recall, RecallPreviewDto> = RecallMapper()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getRecalls()
    }

    override fun getRecalls() {
        if (company == "")
            return
        repository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .filter{val lowerQuery = company.toLowerCase()
                it.company.name.toLowerCase().startsWith(lowerQuery) ||
                it.student.name.toLowerCase().startsWith(lowerQuery)}
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

    private fun checkAndShow(recalls: List<RecallPreviewDto>){
        if(recalls.isEmpty())
            viewState.showEmpty()
        else
            viewState.showRecalls(recalls)
    }
}