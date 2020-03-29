package com.khair.appforitis.presentation.main.profile

import com.khair.appforitis.data.network.AuthenticationProvider
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListProfileRepository
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(var view: ProfileContract.View): ProfileContract.Presenter {

    private var repository: Repository<Profile> = ArrayListProfileRepository()

    override fun getProfile() {
        repository.get()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate { view.hideLoading() }
            .subscribe(
                { profile -> view.showProfile(profile) },
                { exception -> when(exception){
                    is IllegalAccessException -> view.openLoginPage()
                    else -> view.showError(exception.message ?: unknownException)
                } }
            )
    }
}