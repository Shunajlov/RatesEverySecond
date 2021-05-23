package com.ihavenodomain.rateseverysecond.model.remote

import com.ihavenodomain.rateseverysecond.model.CurrencyInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("latest")
    fun getCurrencyInfo(@Query("base") base: String): Single<CurrencyInfo>
}