package com.khair.appforitis.presentation.profileediting

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.khair.appforitis.data.repositoryimpl.CompanyRepository
import com.khair.appforitis.data.repositoryimpl.ProfileRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListCompanyRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListProfileRepository
import com.khair.appforitis.domain.entity.Company
import com.khair.appforitis.domain.entity.CompanyItem
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.presentation.profileediting.dto.CompanyDto
import com.khair.appforitis.presentation.profileediting.dto.ProfileDto
import com.khair.appforitis.presentation.recallcreation.dto.CompanyItemDto
import com.khair.appforitis.unknownException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class ProfileEditingPresenter(): MvpPresenter<ProfileEditingContract.View>(), ProfileEditingContract.Presenter{

//    private var repository: Repository<Profile> = ArrayListProfileRepository()
//    private var companyRepository: Repository<Company> = ArrayListCompanyRepository()
    private var repository: Repository<Profile> = ProfileRepository()
    private var companyRepository: Repository<Company> = CompanyRepository()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCompanies()
        getProfile()
    }

    override fun getCompanies() {
        companyRepository.getAll()
            .concatMap { Flowable.fromIterable(it) }
            .map {
                CompanyDto(
                    it.id,
                    it.name
                )
            }.toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{ viewState.showLoading() }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { companies ->
                    viewState.fillSpinnerWithCompanies(companies) },
                { exception -> when(exception){
                    is IllegalAccessException -> viewState.openLoginPage()
                    else -> viewState.showError(exception.message ?: unknownException)
                } }
            )
    }

    override fun getProfile() {
        repository.get()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{ viewState.showLoading() }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { profile -> viewState.showProfile(profile) },
                { exception -> when(exception){
                    is IllegalAccessException -> viewState.openLoginPage()
                    else -> viewState.showError(exception.message ?: unknownException)
                } }
            )
    }

    override fun saveProfile(profile: ProfileDto) {
        if (profile.isFullFilled()){
            repository.add(
                Profile(
                    0,
                    profile.name,
                    CompanyItem(profile.company.id, profile.company.name),
                    profile.phone,
                    profile.vk,
                    profile.telegram,
                    profile.facebook,
                    profile.additionalDescription
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{ viewState.showLoading() }
                .doOnTerminate{ viewState.hideLoading() }
                .subscribe (
                    { viewState.finishActivity() },
                    { exception -> when(exception){
                        is IllegalAccessException -> viewState.openLoginPage()
                        else -> viewState.showError(exception.message ?: unknownException)
                    } }
                )
        } else {
            val message: String = when {
                profile.name.isBlank() -> {
                    "Имя не может быть пустым"
                }
                profile.phone.isBlank() && profile.vk.isBlank() &&
                        profile.telegram.isBlank() && profile.facebook.isBlank() -> {
                    "Хотя бы один контакт должен быть заполнен"
                }
                else -> return // unknownError
            }
            viewState.showError(message)
        }
    }
}