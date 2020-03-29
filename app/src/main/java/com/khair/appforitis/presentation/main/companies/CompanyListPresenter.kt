package com.khair.appforitis.presentation.main.companies

import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListCompanyRepository
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.domain.mapper.OneWayMapper
import com.khair.appforitis.presentation.main.companies.dto.CompanyPreviewDto
import com.khair.appforitis.unknownException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CompanyListPresenter(var view: CompanyListContract.View): CompanyListContract.Presenter {

    private var companyRepository: Repository<Company> =
        ArrayListCompanyRepository()
    private var companyMapper: OneWayMapper<Company, CompanyPreviewDto> = CompanyMapper()

    override fun getCompanies() {
        companyRepository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .map {
                companyMapper.map(it)
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate { view.hideLoading() }
            .subscribe(
                { companies -> checkAndShow(companies) },
                { exception -> when(exception){
                    is IllegalAccessException -> view.openLoginPage()
                    else -> view.showError(exception.message ?: unknownException)
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
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate { view.hideLoading() }
            .subscribe(
                { companies -> checkAndShow(companies) },
                { exception -> view.showError(exception.message ?: unknownException) }
            )
    }

    private fun checkAndShow(companies: List<CompanyPreviewDto>){
        if(companies.isEmpty())
            view.showEmpty()
        else
            view.showCompanies(companies)
    }
}