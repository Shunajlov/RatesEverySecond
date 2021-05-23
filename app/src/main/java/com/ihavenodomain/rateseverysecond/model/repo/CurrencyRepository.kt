package com.ihavenodomain.rateseverysecond.model.repo

import com.ihavenodomain.rateseverysecond.model.CurrencyInfo
import io.reactivex.Single

interface CurrencyRepository {
    fun getCurrencyInfo(baseCurrency: String): Single<CurrencyInfo>
}