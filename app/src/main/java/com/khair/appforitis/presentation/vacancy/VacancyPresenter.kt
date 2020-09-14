package com.khair.appforitis.presentation.vacancy

import com.khair.appforitis.data.repositoryimpl.VacancyRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListVacancyRepository
import com.khair.appforitis.domain.entity.Vacancy
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit

@InjectViewState
class VacancyPresenter(val id: Long): MvpPresenter<VacancyContract.View>(),VacancyContract.Presenter {

//    private val vacancyRepository: Repository<Vacancy> =
//        ArrayListVacancyRepository()
    private val vacancyRepository: Repository<Vacancy> = VacancyRepository()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getVacancy(id)
    }

    override fun getVacancy(id: Long) {
        vacancyRepository.get(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .doOnTerminate{ viewState.hideLoading() }
            .subscribe(
                {vacancy -> viewState.showVacancy(vacancy)},
                {exception -> when(exception){
                    is IllegalAccessException -> viewState.openLoginPage()
                    else -> viewState.showException(exception.message ?: unknownException)
                }}
            )
    }
}