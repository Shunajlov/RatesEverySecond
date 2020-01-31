package com.ihavenodomain.rateseverysecond.utils

import timber.log.Timber

object ErrorHandler {

    fun handle(throwable: Throwable) {
        Timber.e(throwable)
    }
}