package com.khair.appforitis.app

import android.app.Application
import com.khair.appforitis.data.network.AuthenticationProvider

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        AuthenticationProvider.init(this)
    }
}