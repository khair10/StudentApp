package com.khair.appforitis.presentation.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khair.appforitis.R
import com.khair.appforitis.data.network.AuthenticationProvider
import com.khair.appforitis.presentation.login.LoginActivity
import com.khair.appforitis.presentation.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (AuthenticationProvider.isAuthenticated()) {
            MainActivity.start(this)
        } else {
            LoginActivity.start(this)
        }
    }
}
