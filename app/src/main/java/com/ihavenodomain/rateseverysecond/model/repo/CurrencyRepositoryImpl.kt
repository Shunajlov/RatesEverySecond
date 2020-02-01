package com.ihavenodomain.rateseverysecond.model.repo

import com.ihavenodomain.rateseverysecond.model.CurrencyInfo
import com.ihavenodomain.rateseverysecond.model.remote.ApiConnection
import io.reactivex.Flowable

class CurrencyRepositoryImpl: CurrencyRepository {

    override fun getCurrencyInfo(baseCurrency: String): Flowable<CurrencyInfo> {
        return ApiConnection.instance.api.getCurrencyInfo(baseCurrency)
    }
}