package com.khair.appforitis.presentation.profile

import com.khair.appforitis.data.repositoryimpl.ProfileRepository
import com.khair.appforitis.data.repositoryimpl.temporary.ArrayListProfileRepository
import com.khair.appforitis.domain.entity.Profile
import com.khair.appforitis.domain.repository.Repository
import com.khair.appforitis.unknownException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(var view: ProfileContract.View): ProfileContract.Presenter {

//    private var repository: Repository<Profile> = ArrayListProfileRepository()
private var repository: Repository<Profile> = ProfileRepository()

    override fun getProfile(id: Long) {
        repository.get(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doOnTerminate{ view.hideLoading() }
            .subscribe(
                {profile -> view.showProfile(profile)},
                {exception -> when(exception){
                    is IllegalAccessException -> view.openLoginPage()
                    else -> view.showException(exception.message ?: unknownException)
                }}
            )
    }
}