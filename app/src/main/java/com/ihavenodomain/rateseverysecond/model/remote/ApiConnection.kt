package com.ihavenodomain.rateseverysecond.model.remote

import com.ihavenodomain.rateseverysecond.BuildConfig
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConnection {
    val api: Api by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(1, 2, TimeUnit.SECONDS))
            .build()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BuildConfig.apiEndpoint)
            .build()
        retrofit.create(Api::class.java)
    }
}