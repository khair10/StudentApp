package com.khair.appforitis.presentation.main.companies

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.khair.appforitis.data.repositoryimpl.CompanyRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListCompanyRepository
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.domain.mapper.OneWayMapper
import com.khair.appforitis.presentation.main.companies.dto.CompanyPreviewDto
import com.khair.appforitis.unknownException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class CompanyListPresenter(): MvpPresenter<CompanyListContract.View>(), CompanyListContract.Presenter {

//    private var companyRepository: Repository<Company> =
//        ArrayListCompanyRepository()
    private var companyRepository: Repository<Company> = CompanyRepository()
    private var companyMapper: OneWayMapper<Company, CompanyPreviewDto> = CompanyMapper()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCompanies()
    }

    override fun getCompanies() {
        companyRepository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .map {
                companyMapper.map(it)
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { companies -> checkAndShow(companies) },
                { exception -> when(exception){
                    is IllegalAccessException -> viewState.openLoginPage()
                    else -> viewState.showError(exception.message ?: unknownException)
                } }
            )
    }

    //TODO make api call with query
    override fun getCompaniesWithQuery(query: String?) {
        if(query == null){
            return
        }
        companyRepository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .filter {
                val lowerQuery = query.toLowerCase()
                it.name.toLowerCase().startsWith(lowerQuery)
            }
            .map {
                companyMapper.map(it)
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { companies -> checkAndShow(companies) },
                { exception -> viewState.showError(exception.message ?: unknownException) }
            )
    }

    private fun checkAndShow(companies: List<CompanyPreviewDto>){
        if(companies.isEmpty())
            viewState.showEmpty()
        else
            viewState.showCompanies(companies)
    }
}