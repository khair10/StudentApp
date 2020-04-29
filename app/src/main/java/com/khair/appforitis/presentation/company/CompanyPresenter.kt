package com.khair.appforitis.presentation.company

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.khair.appforitis.data.repositoryimpl.CompanyRepository
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class CompanyPresenter(val id: Long): MvpPresenter<CompanyContract.View>(), CompanyContract.Presenter {

//    private val companyRepository: Repository<Company> = ArrayListCompanyRepository()
    private val companyRepository: Repository<Company> = CompanyRepository()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCompany(id)
    }

    override fun getCompany(id: Long) {
        companyRepository.get(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .doOnTerminate{ viewState.hideLoading() }
            .subscribe(
                { company -> viewState.showCompany(company) },
                { exception ->
                    when(exception){
                        is IllegalAccessException -> viewState.openLoginPage()
                        else -> viewState.showException(exception.message ?: unknownException)
                    }
                }
            )
    }
}