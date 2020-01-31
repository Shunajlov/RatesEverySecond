package com.ihavenodomain.rateseverysecond.model

data class CurrencyRate(
    var name: String = "",
    var value: Double = 1.0,
    var isBase: Boolean = false
) {
    fun calculatePayload(oldItem: CurrencyRate): CurrencyPayload? {
        val name = if (oldItem.name != this.name) this.name else null
        val value = if (oldItem.value != this.value) this.value else null
        val isBase = if (oldItem.isBase != this.isBase) this.isBase else null

        if (name == null && isBase == null && value == null) return null

        return CurrencyPayload(name, value,isBase)
    }

    companion object {
        fun create(name: String, value: Double, isBase: Boolean): CurrencyRate {
            return CurrencyRate(name, value, isBase)
        }

        fun create(name: String, value: Double): CurrencyRate {
            return CurrencyRate(name, value)
        }
    }
}