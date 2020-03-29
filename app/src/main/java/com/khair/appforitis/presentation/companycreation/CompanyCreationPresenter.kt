package com.khair.appforitis.presentation.companycreation

import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListCompanyRepository
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.presentation.companycreation.dto.CompanyCreationDto
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CompanyCreationPresenter(var view: CompanyCreationContract.View): CompanyCreationContract.Presenter {

    private val repository: Repository<Company> = ArrayListCompanyRepository()

    override fun addCompany(item: CompanyCreationDto) {
        if (item.isFullFilled()){
            repository.add(
                Company(
                    0,
                    item.name,
                    item.address,
                    item.webSite,
                    item.contactNumber,
                    item.info,
                    0f,
                    0
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{ view.showLoading() }
                .doOnTerminate{ view.hideLoading() }
                .subscribe(
                    { view.finishActivity() },
                    { exception -> when(exception){
                        is IllegalAccessException -> view.openLoginPage()
                        else -> view.showError(exception.message ?: unknownException)
                    } }
                )
        } else {
            val message: String = when {
                item.name.isBlank() -> {
                    "Название не может быть пустым"
                }
                item.address.isBlank() -> {
                    "Адрес не может быть пустым"
                }
                item.webSite.isBlank() -> {
                    "Веб-сайт не может быть пустым"
                }
                item.contactNumber.isBlank() -> {
                    "Номер контактного телефона не может быть пустым"
                }
                item.info.isBlank() -> {
                    "Информация о компании не может быть пустой"
                }
                else -> return
            }
            view.showError(message)
        }
    }
}