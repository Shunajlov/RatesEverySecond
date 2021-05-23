package com.ihavenodomain.rateseverysecond

import android.app.Application
import com.ihavenodomain.rateseverysecond.di.data.NetworkModule
import com.ihavenodomain.rateseverysecond.di.data.RepositoryModule
import com.ihavenodomain.rateseverysecond.di.domain.InteractorModule
import com.ihavenodomain.rateseverysecond.di.presentation.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        assembleGraph()
    }

    private fun assembleGraph() {
        val graph = listOf(
            NetworkModule.module,
            ViewModelModule.module,
            RepositoryModule.module,
            InteractorModule.module
        )

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(graph)
        }
    }
}