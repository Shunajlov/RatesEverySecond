package com.ihavenodomain.rateseverysecond.model.repo

import com.ihavenodomain.rateseverysecond.model.CurrencyInfo
import com.ihavenodomain.rateseverysecond.model.remote.Api
import io.reactivex.Single

class CurrencyRepositoryImpl(
    private val api: Api
): CurrencyRepository {

    override fun getCurrencyInfo(baseCurrency: String): Single<CurrencyInfo> {
        return api.getCurrencyInfo(baseCurrency)
    }
}