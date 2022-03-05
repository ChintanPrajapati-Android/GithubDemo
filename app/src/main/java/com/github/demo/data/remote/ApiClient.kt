package com.github.demo.data.remote

import com.google.gson.GsonBuilder
import com.github.demo.BuildConfig
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        private const val NETWORK_READ_TIME_OUT = 2L
        private const val NETWORK_CONNECT_TIME_OUT = 60L

        private var SINGLETON_INSTANCE: Retrofit? = null

        fun getInstance(baseUrl: String): Retrofit {
            if (SINGLETON_INSTANCE == null) {
                SINGLETON_INSTANCE = Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(
                        GsonConverterFactory.create(
                            GsonBuilder().setLenient().create()
                        )
                    )
                    .client(getHttpClient()).build()
            }
            return SINGLETON_INSTANCE!!
        }

        private fun getHttpClient(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(NETWORK_READ_TIME_OUT, TimeUnit.MINUTES)
            httpClient.connectTimeout(NETWORK_CONNECT_TIME_OUT, TimeUnit.SECONDS)

            val cookieHandler: CookieHandler = CookieManager()
            httpClient.cookieJar(JavaNetCookieJar(cookieHandler))

            /**
             * Add Logging interceptors for see the API response in debug application
             */
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logging)
            }
            return httpClient.build()
        }

        /**
         * Method used to disconnect the previous server when user disconnect the app
         */
        fun setDefaultUrl() {
            SINGLETON_INSTANCE = null
        }
    }
}