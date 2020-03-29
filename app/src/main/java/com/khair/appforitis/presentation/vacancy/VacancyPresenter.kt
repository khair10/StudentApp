package com.khair.appforitis.presentation.vacancy

import com.khair.appforitis.data.repositoryimpl.VacancyRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListVacancyRepository
import com.khair.appforitis.domain.entity.Vacancy
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class VacancyPresenter(var view: VacancyContract.View): VacancyContract.Presenter {

    private val vacancyRepository: Repository<Vacancy> =
        ArrayListVacancyRepository()

    override fun getVacancy(id: Long) {
        vacancyRepository.get(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate{ view.hideLoading() }
            .subscribe(
                {vacancy -> view.showVacancy(vacancy)},
                {exception -> when(exception){
                    is IllegalAccessException -> view.openLoginPage()
                    else -> view.showException(exception.message ?: unknownException)
                }}
            )
    }
}