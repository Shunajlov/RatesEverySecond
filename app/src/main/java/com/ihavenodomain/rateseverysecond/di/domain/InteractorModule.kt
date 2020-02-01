package com.ihavenodomain.rateseverysecond.di.domain

import com.ihavenodomain.rateseverysecond.domain.CurrencyInteractor
import com.ihavenodomain.rateseverysecond.domain.CurrencyInteractorImpl
import org.koin.dsl.bind
import org.koin.dsl.module


object InteractorModule {
    val module by lazy {
        module {
            single { CurrencyInteractorImpl(get()) } bind CurrencyInteractor::class
        }
    }
}