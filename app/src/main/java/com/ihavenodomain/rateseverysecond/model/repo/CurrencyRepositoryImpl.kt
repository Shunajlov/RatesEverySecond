package com.ihavenodomain.rateseverysecond.model.repo

import com.ihavenodomain.rateseverysecond.model.CurrencyRate
import com.ihavenodomain.rateseverysecond.model.remote.ApiConnection
import io.reactivex.Flowable
import java.util.*

class CurrencyRepositoryImpl: CurrencyRepository {

    override fun getCurrencyInfo(baseCurrency: String, multiplier: Double): Flowable<List<CurrencyRate>> {
        return ApiConnection.instance.api.getCurrencyInfo(baseCurrency)
            .map { currencyInfo ->
                getRatesList(baseCurrency, currencyInfo.rates, multiplier)
            }
    }

    companion object {
        private fun getRatesList(baseCurrency: String, rates: Map<String, Double>?, multiplier: Double) : List<CurrencyRate> {
            if (rates == null || rates.isEmpty()) return listOf()

            val cRates = mutableListOf<CurrencyRate>()

            cRates.add(CurrencyRate.create(baseCurrency, multiplier, true))

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
}