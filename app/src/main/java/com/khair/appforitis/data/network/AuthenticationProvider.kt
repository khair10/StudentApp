package com.khair.appforitis.data.network

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.khair.appforitis.domain.AuthProvider
import com.khair.appforitis.domain.entity.Authentication

object AuthenticationProvider: AuthProvider {

    const val AUTH_SHARED_PREFERENCES = "com.khair.appforitis.data.network.AUTH_SHARED_PREFERENCES"
    const val AUTH_KEY = "com.khair.appforitis.data.network.AUTH_KEY"
    const val IS_AUTHENTICATED_KEY = "com.khair.appforitis.data.network.IS_AUTHENTICATED_KEY"
    val gson = Gson()
    var sharedPreferences: SharedPreferences? = null
    var authentication: Authentication? = null

    fun init(context: Context){
        sharedPreferences = context.getSharedPreferences(AUTH_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun saveAuthentication(authentication: Authentication) {
        this.authentication = authentication
        sharedPreferences?.edit()
            ?.putString(AUTH_KEY, gson.toJson(authentication))
            ?.putBoolean(IS_AUTHENTICATED_KEY, true)
            ?.apply()
    }

    override fun fetchAuthentication(): Authentication {
        if(authentication != null){
            return authentication as Authentication
        } else {
            // опасно
            return gson.fromJson(sharedPreferences?.getString(AUTH_KEY, null), Authentication::class.java)
        }
    }

    override fun isAuthenticated(): Boolean {
        return sharedPreferences?.getBoolean(IS_AUTHENTICATED_KEY, false) ?: false
    }

    override fun clear() {
        authentication = null
        sharedPreferences?.edit()?.clear()?.apply()
    }
}