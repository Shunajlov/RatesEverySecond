package com.ihavenodomain.rateseverysecond.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihavenodomain.rateseverysecond.App
import com.ihavenodomain.rateseverysecond.model.CurrencyRate
import com.ihavenodomain.rateseverysecond.model.repo.CurrencyRepository
import com.ihavenodomain.rateseverysecond.model.repo.CurrencyRepositoryImpl
import com.ihavenodomain.rateseverysecond.utils.ErrorHandler
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val repository: CurrencyRepository
) : ViewModel() {
    private var baseCurrency = App.DEFAULT_CURRENCY
    private var multiplier: Double = 1.0

    private var currencyInfoDisposable: Disposable? = null
    private var currencyLiveData : MutableLiveData<List<CurrencyRate>> = MutableLiveData()
    fun observeCurrencyInfo(): LiveData<List<CurrencyRate>> = currencyLiveData

    init {
        startObserveCurrencyRates()
    }

    private fun startObserveCurrencyRates() {
        unbind()
        currencyInfoDisposable =
            Flowable.interval(0, 1, TimeUnit.SECONDS, Schedulers.single())
                .subscribeOn(Schedulers.io())
                .flatMap {
                    repository.getCurrencyInfo(baseCurrency, multiplier)
                }
                .doOnError(ErrorHandler::handle)
                .retry()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list ->
                        currencyLiveData.value = list
                    }, ErrorHandler::handle)
    }

    private fun unbind() {
        if (currencyInfoDisposable == null) return
        if (!currencyInfoDisposable!!.isDisposed) currencyInfoDisposable!!.dispose()
    }

    fun updateBaseCurrency(position: Int) {
        // TODO: 2020-01-31 update base currency immediately
    }

    fun updateMultiplier(value: Double) {
        multiplier = value
        // TODO: 2020-01-31 update multiplier immediately
    }

    override fun onCleared() {
        super.onCleared()
        unbind()
    }
}
