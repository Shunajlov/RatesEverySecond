package com.ihavenodomain.rateseverysecond.model

data class CurrencyPayload(
    val name: String? = "",
    val value: Double? = 0.0,
    val isBase: Boolean? = false
)