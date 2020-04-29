package com.khair.appforitis.app

import android.app.Application
import com.khair.appforitis.data.network.AuthenticationProvider
import io.realm.Realm

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        AuthenticationProvider.init(this)
    }
}