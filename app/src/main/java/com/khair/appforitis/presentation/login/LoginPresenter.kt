package com.khair.appforitis.presentation.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.khair.appforitis.data.network.AuthenticationProvider
import com.khair.appforitis.data.repositoryimpl.LoginRepository
import com.khair.appforitis.domain.entity.Authentication
import com.khair.appforitis.domain.entity.LoginForm
import com.khair.appforitis.domain.repository.AuthRepository
import com.khair.appforitis.presentation.login.dto.LoginDto
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class LoginPresenter: MvpPresenter<LoginContract.View>(), LoginContract.Presenter {

    private val loginRepository: AuthRepository<LoginForm> = LoginRepository()

    override fun login(loginForm: LoginDto) {
        if(loginForm.isFullFilled()){
            loginRepository.login(
                LoginForm(
                    loginForm.login!!,
                    loginForm.password!!
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{ viewState.showLoading() }
                .doOnTerminate{ viewState.hideLoading() }
                .subscribe(
                    { next ->
                        AuthenticationProvider.saveAuthentication(Authentication(next.id, next.name, next.jsonToken, next.refreshToken))
                        viewState.openHome()
                    },
                    { exception -> viewState.showError(exception?.message ?: unknownException) }
                )
        }else{
            val message: String = when {
                loginForm.login?.isBlank() ?: true -> {
                    "Логин не может быть пустым"
                }
                loginForm.password?.isBlank() ?: true -> {
                    "Пароль не может быть пустым"
                }
                else -> return
            }
            viewState.showError(message)
        }
    }
}