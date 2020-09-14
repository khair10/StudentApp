package com.khair.appforitis.data.network

import com.khair.appforitis.BuildConfig
import com.khair.appforitis.data.model.NetworkAuthentication
import com.khair.appforitis.data.network.api.AuthService
import com.khair.appforitis.domain.entity.Authentication
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RefreshAuthenticator: Authenticator {

    private val authProvider = AuthenticationProvider
    private val notLoggedResponseCode = 401
    private val successResponseCode = 200

    override fun authenticate(route: Route?, response: Response): Request? {

        if (!response.request.header("Authorization").equals(authProvider.authentication?.jsonToken)) {
            authProvider.clear()
            return null
        }
        if (response.code == notLoggedResponseCode && authProvider.authentication != null && authProvider.isAuthenticated()) {

            val refreshTokenResponse: retrofit2.Response<NetworkAuthentication> = Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthService::class.java)
                .refresh(authProvider.fetchAuthentication().refreshToken)
                .execute()

            if (refreshTokenResponse.code() == successResponseCode) {
                refreshTokenResponse.body()?.let {
                    authProvider.saveAuthentication(
                        Authentication(
                            it.studentItemDto.id,
                            it.studentItemDto.name,
                            it.jwtToken,
                            it.refreshToken
                        )
                    )
                }
                return response.request
                    .newBuilder()
                    .header("Authorization", authProvider.fetchAuthentication().jsonToken)
                    .build()
            } else {
                return null
            }
        } else {
            return null
        }
    }
}