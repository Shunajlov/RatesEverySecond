package com.ihavenodomain.rateseverysecond.model.repo

import com.ihavenodomain.rateseverysecond.model.CurrencyInfo
import io.reactivex.Flowable

interface CurrencyRepository {
    fun getCurrencyInfo(baseCurrency: String): Flowable<CurrencyInfo>
}