package com.khair.appforitis.presentation.main.vacancies

import com.khair.appforitis.data.repositoryimpl.VacancyRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListVacancyRepository
import com.khair.appforitis.domain.entity.Vacancy
import com.khair.appforitis.domain.mapper.OneWayMapper
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.presentation.main.vacancies.dto.VacancyPreviewDto
import com.khair.appforitis.unknownException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class VacancyListPresenter(): MvpPresenter<VacancyListContract.View>(), VacancyListContract.Presenter {

//    private val vacancyRepository: Repository<Vacancy> =
//        ArrayListVacancyRepository()
private val vacancyRepository: Repository<Vacancy> =
    VacancyRepository()
    private val vacancyMapper: OneWayMapper<Vacancy, VacancyPreviewDto> = VacancyMapper()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getVacancies()
    }

    override fun getVacancies() {
        vacancyRepository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .sorted { r1, r2 -> r1.date.compareTo(r2.date)}
            .map { vacancyMapper.map(it) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { vacancies -> checkAndShow(vacancies) },
                { exception -> when(exception){
                    is IllegalAccessException -> viewState.openLoginPage()
                    else -> viewState.showError(exception.message ?: unknownException)
                } }
            )
    }

    override fun getVacancies(query: String?) {
        if(query == null){
            return
        }
        vacancyRepository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .filter {
                val lowerQuery = query.toLowerCase()
                it.name.toLowerCase().startsWith(lowerQuery) ||
                        it.company.name.toLowerCase().startsWith(lowerQuery)
            }
            .map { vacancyMapper.map(it) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { vacancies -> checkAndShow(vacancies) },
                { exception -> when(exception){
                    is IllegalAccessException -> viewState.openLoginPage()
                    else -> viewState.showError(exception.message ?: unknownException)
                } }
            )
    }

    private fun checkAndShow(vacancies: List<VacancyPreviewDto>){
        if(vacancies.isEmpty())
            viewState.showEmpty()
        else
            viewState.showVacancies(vacancies)
    }
}