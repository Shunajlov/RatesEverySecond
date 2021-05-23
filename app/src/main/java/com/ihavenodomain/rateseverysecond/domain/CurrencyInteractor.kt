package com.ihavenodomain.rateseverysecond.domain

import com.ihavenodomain.rateseverysecond.model.CurrencyInfo
import com.ihavenodomain.rateseverysecond.model.CurrencyRate
import io.reactivex.Single

interface CurrencyInteractor {
    var baseCurrency: String

    var currencyInfo: CurrencyInfo?

    var multiplier: Double

    fun observeCurrencyList(): Single<List<CurrencyRate>>

    fun getRatesList(): List<CurrencyRate>
}