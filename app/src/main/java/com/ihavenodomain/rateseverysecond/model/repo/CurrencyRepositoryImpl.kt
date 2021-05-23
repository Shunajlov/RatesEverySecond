package com.ihavenodomain.rateseverysecond.model.repo

import com.ihavenodomain.rateseverysecond.model.CurrencyInfo
import com.ihavenodomain.rateseverysecond.model.remote.Api
import io.reactivex.Flowable

class CurrencyRepositoryImpl(
    private val api: Api
): CurrencyRepository {

    override fun getCurrencyInfo(baseCurrency: String): Flowable<CurrencyInfo> {
        return api.getCurrencyInfo(baseCurrency)
    }
}