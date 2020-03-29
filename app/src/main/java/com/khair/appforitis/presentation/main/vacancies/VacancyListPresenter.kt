package com.khair.appforitis.presentation.main.vacancies

import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListVacancyRepository
import com.khair.appforitis.domain.entity.Vacancy
import com.khair.appforitis.domain.mapper.OneWayMapper
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.presentation.main.vacancies.dto.VacancyPreviewDto
import com.khair.appforitis.unknownException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class VacancyListPresenter(private var view: VacancyListContract.View): VacancyListContract.Presenter {

    private val vacancyRepository: Repository<Vacancy> =
        ArrayListVacancyRepository()
    private val vacancyMapper: OneWayMapper<Vacancy, VacancyPreviewDto> = VacancyMapper()

    override fun getVacancies() {
        vacancyRepository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .map { vacancyMapper.map(it) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate { view.hideLoading() }
            .subscribe(
                { vacancies -> checkAndShow(vacancies) },
                { exception -> when(exception){
                    is IllegalAccessException -> view.openLoginPage()
                    else -> view.showError(exception.message ?: unknownException)
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
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate { view.hideLoading() }
            .subscribe(
                { vacancies -> checkAndShow(vacancies) },
                { exception -> when(exception){
                    is IllegalAccessException -> view.openLoginPage()
                    else -> view.showError(exception.message ?: unknownException)
                } }
            )
    }

    private fun checkAndShow(vacancies: List<VacancyPreviewDto>){
        if(vacancies.isEmpty())
            view.showEmpty()
        else
            view.showVacancies(vacancies)
    }
}