package com.khair.appforitis.data.network

import okhttp3.Interceptor
import okhttp3.Response

class JwtInterceptor: Interceptor {

    private val authProvider = AuthenticationProvider

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url.encodedPath.equals("/login", true) ||
            request.url.encodedPath.equals("/registrate", true) ||
            request.url.encodedPath.equals("/refresh", true)
        ) {
            return chain.proceed(request)
        }

        if(!authProvider.isAuthenticated()){
            return chain.proceed(request)
        }

        val newRequest = request.newBuilder()
            .addHeader("Authorization", authProvider.fetchAuthentication().jsonToken)
            .build()
        return chain.proceed(newRequest)
    }
}