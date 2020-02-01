package com.ihavenodomain.rateseverysecond.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyInfo(
    @SerializedName("base")
    @Expose
    var base: String = "",
    @SerializedName("date")
    @Expose
    var date: String? = null,
    @SerializedName("rates")
    @Expose
    var rates: Map<String, Double>? = null
)