package com.khair.appforitis.presentation.company

import com.khair.appforitis.data.repositoryimpl.CompanyRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListCompanyRepository
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CompanyPresenter(var view: CompanyContract.View): CompanyContract.Presenter {

//    private val companyRepository: Repository<Company> = ArrayListCompanyRepository()
    private val companyRepository: Repository<Company> = CompanyRepository()

    override fun getCompany(id: Long) {
        companyRepository.get(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate{ view.hideLoading() }
            .subscribe(
                { company -> view.showCompany(company) },
                { exception ->
                    when(exception){
                        is IllegalAccessException -> view.openLoginPage()
                        else -> view.showException(exception.message ?: unknownException)
                    }
                }
            )
    }
}