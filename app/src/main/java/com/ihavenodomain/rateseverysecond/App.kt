package com.ihavenodomain.rateseverysecond

import android.app.Application
import com.ihavenodomain.rateseverysecond.di.data.RepositoryModule
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

        PACKAGE_NAME = packageName

        assembleGraph()

    }

    private fun assembleGraph() {
        val graph = listOf(
            ViewModelModule.module,
            RepositoryModule.module
        )

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(graph)
        }
    }

    companion object {
        const val DEFAULT_CURRENCY = "EUR"
        var PACKAGE_NAME: String = ""
    }
}