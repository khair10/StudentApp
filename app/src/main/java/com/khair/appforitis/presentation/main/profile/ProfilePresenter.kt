package com.khair.appforitis.presentation.main.profile

import com.khair.appforitis.data.network.AuthenticationProvider
import com.khair.appforitis.data.repositoryimpl.ProfileRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListProfileRepository
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ProfilePresenter(): MvpPresenter<ProfileContract.View>(), ProfileContract.Presenter {

//    private var repository: Repository<Profile> = ArrayListProfileRepository()
    private var repository: Repository<Profile> = ProfileRepository()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getProfile()
    }

    override fun getProfile() {
        repository.get()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading() }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { profile -> viewState.showProfile(profile) },
                { exception -> when(exception){
                    is IllegalAccessException -> viewState.openLoginPage()
                    else -> {
                        exception.printStackTrace()
                        viewState.showError(exception.message ?: unknownException)
                    }
                } }
            )
    }
}