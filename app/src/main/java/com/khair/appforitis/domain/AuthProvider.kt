package com.khair.appforitis.domain

import com.khair.appforitis.domain.entity.Authentication

interface AuthProvider {

    fun saveAuthentication(authentication: Authentication)
    fun fetchAuthentication(): Authentication
    fun isAuthenticated(): Boolean
    fun clear()
}