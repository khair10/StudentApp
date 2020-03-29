package com.khair.appforitis.presentation.login

import com.khair.appforitis.data.repositoryimpl.LoginRepository
import com.khair.appforitis.domain.entity.LoginForm
import com.khair.appforitis.domain.repository.AuthRepository
import com.khair.appforitis.presentation.login.dto.LoginDto
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginPresenter(var view: LoginContract.View): LoginContract.Presenter {

    private val loginRepository: AuthRepository<LoginForm> = LoginRepository()

    override fun login(loginForm: LoginDto) {
        if(loginForm.isFullFilled()){
            loginRepository.login(
                LoginForm(
                    loginForm.login,
                    loginForm.password
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{ view.showLoading() }
                .doOnTerminate{ view.hideLoading() }
                .subscribe(
                    { view.openHome() },
                    { exception -> view.showError(exception?.message ?: unknownException) }
                )
        }else{
            val message: String = when {
                loginForm.login.isBlank() -> {
                    "Логин не может быть пустым"
                }
                loginForm.password.isBlank() -> {
                    "Пароль не может быть пустым"
                }
                else -> return
            }
            view.showError(message)
        }
    }
}