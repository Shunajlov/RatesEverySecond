package com.ihavenodomain.rateseverysecond.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihavenodomain.rateseverysecond.domain.CurrencyInteractor
import com.ihavenodomain.rateseverysecond.model.CurrencyRate
import com.ihavenodomain.rateseverysecond.utils.ErrorHandler
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val currencyInteractor: CurrencyInteractor
) : ViewModel() {

    private var currencyInfoDisposable: Disposable? = null
    private var currencyLiveData: MutableLiveData<List<CurrencyRate>> = MutableLiveData()

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
                    currencyInteractor.observeCurrencyList().toFlowable()
                }
                .doOnError {
                    currencyLiveData.postValue(listOf())
                }
                .retry()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list ->
                        currencyLiveData.value = list
                    }, ErrorHandler::handle
                )
    }

    private fun unbind() {
        if (currencyInfoDisposable == null) return
        if (!currencyInfoDisposable!!.isDisposed) currencyInfoDisposable!!.dispose()
    }

    fun updateBaseCurrency(position: Int) {
        currencyLiveData.value?.let {
            currencyInteractor.baseCurrency = it[position].name
            currencyInteractor.multiplier = it[position].value
            startObserveCurrencyRates()
        }
    }

    fun updateMultiplier(value: Double) {
        currencyInteractor.multiplier = value
        currencyLiveData.value = currencyInteractor.getRatesList()
    }

    override fun onCleared() {
        super.onCleared()
        unbind()
    }
}
