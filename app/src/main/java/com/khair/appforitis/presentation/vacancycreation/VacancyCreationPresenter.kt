package com.khair.appforitis.presentation.vacancycreation

import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListCompanyRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListVacancyRepository
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.entity.CompanyItem
import com.khair.appforitis.domain.entity.StudentItem
import com.khair.appforitis.domain.entity.Vacancy
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.presentation.vacancycreation.dto.CompanyDto
import com.khair.appforitis.presentation.vacancycreation.dto.VacancyCreationDto
import com.khair.appforitis.unknownException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class VacancyCreationPresenter(var view: VacancyCreationContract.View): VacancyCreationContract.Presenter {

    private val companyRepository: Repository<Company> = ArrayListCompanyRepository()
    private val vacancyRepository: Repository<Vacancy> = ArrayListVacancyRepository()

    override fun getCompanies() {
        companyRepository.getAll()
            .flatMap { Flowable.fromIterable(it) }
            .map {
                CompanyDto(
                    it.id,
                    it.name,
                    it.rating,
                    it.recallsCount
                )
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate { view.hideLoading() }
            .subscribe(
                { companies -> view.fillSpinnerWithCompanies(companies)},
                { exception -> when(exception){
                    is IllegalAccessException -> view.openLoginPage()
                    else -> view.showError(exception.message ?: unknownException)
                } }
            )
    }

    override fun addVacancy(item: VacancyCreationDto) {
        if (item.isFullFilled()){
            vacancyRepository.add(
                Vacancy(
                    0,
                    item.name,
                    item.description,
                    CompanyItem(item.companyDto.id, item.companyDto.name),
                    item.companyDto.rating,
                    item.companyDto.recallsCount,
                    item.salary.toInt(),
                    StudentItem(item.studentDto.id, item.studentDto.name),
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
                item.name.isBlank() -> {
                    "Название не может быть пустым"
                }
                item.salary.toIntOrNull() == null -> {
                    "Зарплата не может быть пустой"
                }
                item.companyDto.isFullFilled() -> {
                    "Компания должна быть выбрана"
                }
                item.description.isBlank() -> {
                    "Информация не может быть пустой"
                }
                else -> return
            }
            view.showError(message)
        }
    }
}