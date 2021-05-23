package com.ihavenodomain.rateseverysecond.di.data

import com.ihavenodomain.rateseverysecond.BuildConfig
import com.ihavenodomain.rateseverysecond.model.remote.Api
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import org.koin.dsl.module

object NetworkModule {
    val module = module {
        factory { provideOkHttpClient() }
        single { provideRetrofit(get()) }
        factory { provideApi(get()) }
    }

    private fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BuildConfig.apiEndpoint)
            .build()

    private fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(1, 5, TimeUnit.SECONDS))
            .build()

    private fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}