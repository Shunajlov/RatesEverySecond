package com.ihavenodomain.rateseverysecond.domain

import com.ihavenodomain.rateseverysecond.model.CurrencyInfo
import com.ihavenodomain.rateseverysecond.model.CurrencyRate
import com.ihavenodomain.rateseverysecond.model.repo.CurrencyRepository
import io.reactivex.Single

class CurrencyInteractorImpl(private val repository: CurrencyRepository): CurrencyInteractor {
    override var baseCurrency: String = "EUR"
    override var currencyInfo: CurrencyInfo? = null
    override var multiplier: Double = 1.0

    override fun observeCurrencyList(): Single<List<CurrencyRate>> {
        return repository.getCurrencyInfo(baseCurrency)
            .map {
                this.currencyInfo = it
                getRatesList()
            }
    }

    override fun getRatesList(): List<CurrencyRate> {
        if (currencyInfo == null) return listOf()

        val rates = currencyInfo!!.rates
        if (rates == null || rates.isEmpty()) return listOf()

        val cRates = mutableListOf<CurrencyRate>()

        cRates.add(CurrencyRate.create(currencyInfo!!.base, multiplier, true))

        for((key, value) in rates) {
            cRates.add(
                CurrencyRate.create(
                    key,
                    value * multiplier
                )
            )
        }

        return cRates
    }
}