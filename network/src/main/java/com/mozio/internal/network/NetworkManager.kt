package com.mozio.internal.network

import android.content.Context
import com.mozio.internal.network.interceptors.JwtTokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NetworkManager @Inject constructor(
    context: Context,
    baseUrl: String
) {
    private val connectionTimeOut: Long = 30L

    private val networkLoggerInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val tokenInterceptor = JwtTokenInterceptor(context)

    private val networkClient = OkHttpClient.Builder()
        .addNetworkInterceptor(networkLoggerInterceptor)
        .addInterceptor(tokenInterceptor)
        .callTimeout(connectionTimeOut, TimeUnit.SECONDS)
        .build()

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(networkClient)
        .baseUrl(baseUrl)
        .build()

    /**
     * Call this function to get a ready instance of the Retrofit Interface
     */
    fun <T> create(retrofitClass: Class<T>): T = retrofitBuilder.create(retrofitClass)

}