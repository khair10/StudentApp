package com.khair.appforitis.presentation.recallcreation

import com.khair.appforitis.data.repositoryimpl.CompanyRepository
import com.khair.appforitis.data.repositoryimpl.RecallRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListCompanyRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListRecallRepository
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.entity.CompanyItem
import com.khair.appforitis.domain.entity.Recall
import com.khair.appforitis.domain.entity.StudentItem
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.presentation.recallcreation.dto.CompanyItemDto
import com.khair.appforitis.presentation.recallcreation.dto.RecallCreationDto
import com.khair.appforitis.unknownException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class RecallCreationPresenter(): MvpPresenter<RecallCreationContract.View>(), RecallCreationContract.Presenter {

//    private val companyRepository: Repository<Company> = ArrayListCompanyRepository()
//    private val recallRepository: Repository<Recall> = ArrayListRecallRepository()
private val companyRepository: Repository<Company> = CompanyRepository()
    private val recallRepository: Repository<Recall> = RecallRepository()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCompanies()
    }

    override fun getCompanies() {
        companyRepository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .map {
                CompanyItemDto(
                    it.id,
                    it.name
                )
            }.toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{ viewState.showLoading() }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { companies ->
                    viewState.fillSpinnerWithCompanies(companies) },
                { exception -> when(exception){
                    is IllegalAccessException -> viewState.openLoginPage()
                    else -> viewState.showError(exception.message ?: unknownException)
                } }
            )
    }

    override fun addRecall(item: RecallCreationDto) {
        if (item.isFullFilled()){
            recallRepository.add(
                Recall(
                    0,
                    StudentItem(item.studentItemDto.id, item.studentItemDto.name),
                    item.description,
                    CompanyItem(item.companyItemDto.id, item.companyItemDto.name),
                    item.rating,
                    Date()
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{ viewState.showLoading() }
                .doOnTerminate{ viewState.hideLoading() }
                .subscribe (
                    { viewState.finishActivity() },
                    { exception -> when(exception){
                        is IllegalAccessException -> viewState.openLoginPage()
                        else -> viewState.showError(exception.message ?: unknownException)
                    } }
                )
        } else {
            val message: String = when {
                !item.companyItemDto.isFullFilled() -> {
                    "Компания не может быть пустой"
                }
                item.rating == 0F -> {
                    "Рейтинг не может быть пустым"
                }
                item.description.isBlank() -> {
                    "Информация о компании не может быть пустой"
                }
                else -> return
            }
            viewState.showError(message)
        }
    }
}