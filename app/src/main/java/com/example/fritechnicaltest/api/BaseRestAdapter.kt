package com.example.fritechnicaltest.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BaseRestAdapter {
    private var apiMethods: ApiMethods? = null

    init {
        build()
    }

    private fun build() {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient().build())
            .baseUrl("https://api.unsplash.com/")
            .build()
        setApiMethods(retrofit.create(ApiMethods::class.java))
    }

    private fun getHttpClient(): OkHttpClient.Builder {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(40, TimeUnit.SECONDS)
        httpClient.readTimeout(40L, TimeUnit.SECONDS)
        httpClient.writeTimeout(40L, TimeUnit.SECONDS)
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .build()
            chain.proceed(request)
        }
        return httpClient
    }

    private fun setApiMethods(apiMethods: ApiMethods) {
        this.apiMethods = apiMethods
    }

    fun apiMethods(): ApiMethods? {
        return apiMethods
    }
}