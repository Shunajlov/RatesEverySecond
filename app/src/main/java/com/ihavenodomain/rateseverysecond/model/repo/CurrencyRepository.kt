package com.ihavenodomain.rateseverysecond.model.repo

import com.ihavenodomain.rateseverysecond.model.CurrencyRate
import io.reactivex.Flowable

interface CurrencyRepository {
    fun getCurrencyInfo(baseCurrency: String, multiplier: Double): Flowable<List<CurrencyRate>>
}