package com.khair.appforitis.presentation.registration

import com.khair.appforitis.data.repositoryimpl.RegistrationRepositoryImpl
import com.khair.appforitis.domain.entity.ProfileItem
import com.khair.appforitis.domain.entity.RegistrationForm
import com.khair.appforitis.domain.repository.RegistrationRepository
import com.khair.appforitis.presentation.registration.dto.RegistrationDto
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class RegistrationPresenter(): MvpPresenter<RegistrationContract.View>(), RegistrationContract.Presenter {

    private val registrationRepository: RegistrationRepository<RegistrationForm> = RegistrationRepositoryImpl()

    override fun registration(registrationForm: RegistrationDto) {
        if(registrationForm.isFullFilled()) {
            registrationRepository.registrate(
                RegistrationForm(
                    registrationForm.login,
                    registrationForm.password,
                    registrationForm.passwordConfirm,
                    ProfileItem(
                        registrationForm.profile.name,
                        registrationForm.profile.phone
                    )
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showLoading() }
                .doOnTerminate { viewState.hideLoading() }
                .subscribe(
                    { viewState.openLogin() },
                    { exception -> viewState.showError(exception?.message ?: unknownException) }
                )
        } else {
            val message: String = when {
                registrationForm.profile.name.isBlank() -> {
                    "Имя не может быть пустым"
                }
                registrationForm.profile.name.isBlank() -> {
                    "Телефон не может быть пустым"
                }
                registrationForm.login.isBlank() -> {
                    "Логин не может быть пустым"
                }
                registrationForm.password.isBlank() -> {
                    "Пароль не может быть пустым"
                }
                registrationForm.passwordConfirm.isBlank() -> {
                    "Пароль не может быть пустым"
                }
                registrationForm.password != registrationForm.passwordConfirm -> {
                    "Пароли не совпадают"
                }
                else -> return
            }
            viewState.showError(message)
        }
    }
}