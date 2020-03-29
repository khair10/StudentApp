package com.khair.appforitis.presentation.profileediting

import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListProfileRepository
import com.khair.appforitis.domain.entity.CompanyItem
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.presentation.profileediting.dto.ProfileDto
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfileEditingPresenter(var view: ProfileEditingContract.View): ProfileEditingContract.Presenter{

    private var repository: Repository<Profile> = ArrayListProfileRepository()

    override fun getProfile() {
        repository.get()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{ view.showLoading() }
            .doOnTerminate { view.hideLoading() }
            .subscribe(
                { profile -> view.showProfile(profile) },
                { exception -> when(exception){
                    is IllegalAccessException -> view.openLoginPage()
                    else -> view.showError(exception.message ?: unknownException)
                } }
            )
    }

    override fun setProfile(profile: ProfileDto) {
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
                profile.name.isBlank() -> {
                    "Имя не может быть пустым"
                }
                profile.phone.isBlank() && profile.vk.isBlank() &&
                        profile.telegram.isBlank() && profile.facebook.isBlank() -> {
                    "Хотя бы один контакт должен быть заполнен"
                }
                else -> return // unknownError
            }
            view.showError(message)
        }
    }
}