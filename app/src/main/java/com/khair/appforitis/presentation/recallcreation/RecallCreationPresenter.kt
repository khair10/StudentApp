package com.khair.appforitis.presentation.recallcreation

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
import java.util.*

class RecallCreationPresenter(var view: RecallCreationContract.View): RecallCreationContract.Presenter {

    private val companyRepository: Repository<Company> = ArrayListCompanyRepository()
    private val recallRepository: Repository<Recall> = ArrayListRecallRepository()

    override fun getCompanies() {
        companyRepository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .map {
                CompanyItemDto(
                    it.id,
                    it.name
                )
            }.toList()
            .doOnSubscribe{ view.showLoading() }
            .doOnTerminate { view.hideLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { companies ->
                    view.fillSpinnerWithCompanies(companies) },
                { exception -> when(exception){
                    is IllegalAccessException -> view.openLoginPage()
                    else -> view.showError(exception.message ?: unknownException)
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
                .doOnSubscribe{ view.showLoading() }
                .doOnTerminate{ view.hideLoading() }
                .subscribe (
                    { view.finishActivity() },
                    { exception -> when(exception){
                        is IllegalAccessException -> view.openLoginPage()
                        else -> view.showError(exception.message ?: unknownException)
                    } }
                )
        } else {
            val message: String = when {
                item.companyItemDto.isFullFilled() -> {
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
            view.showError(message)
        }
    }
}