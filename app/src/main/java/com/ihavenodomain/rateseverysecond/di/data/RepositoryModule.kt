package com.ihavenodomain.rateseverysecond.di.data

import com.ihavenodomain.rateseverysecond.model.repo.CurrencyRepository
import com.ihavenodomain.rateseverysecond.model.repo.CurrencyRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

object RepositoryModule {
    val module = module {
        single { CurrencyRepositoryImpl(get()) } bind CurrencyRepository::class
    }
}