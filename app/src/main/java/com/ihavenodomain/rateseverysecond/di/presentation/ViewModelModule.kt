package com.ihavenodomain.rateseverysecond.di.presentation

import com.ihavenodomain.rateseverysecond.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object ViewModelModule {
    val module by lazy {
        module {
            viewModel { MainViewModel(get()) }
        }
    }
}