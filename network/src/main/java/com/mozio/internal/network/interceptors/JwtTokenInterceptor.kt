package com.mozio.internal.network.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class JwtTokenInterceptor(private val context: Context?) : Interceptor {
    companion object {
        const val EXTRA_TOKEN_SPACE = "EXTRA_TOKEN_SPACE"
        const val EXTRA_TOKEN_HEADER = "EXTRA_TOKEN_HEADER"
        const val EXTRA_REFRESH_TOKEN_HEADER = "EXTRA_REFRESH_TOKEN_HEADER"
        const val EXTRA_AUTH_HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val tokenAttachedRequest = chain
            .request()
            .newBuilder()
            .addHeader(EXTRA_AUTH_HEADER, "Bearer ${readTokenFromPref(context)}")

        return chain.proceed(tokenAttachedRequest.build())
    }

    private fun readTokenFromPref(context: Context?) = context
            ?.getSharedPreferences(EXTRA_TOKEN_SPACE, Context.MODE_PRIVATE)
            ?.getString(EXTRA_TOKEN_HEADER, "")
}