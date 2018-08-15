package eu.szwiec.checkittravelkit.vo

import com.squareup.moshi.Json

data class Currency(
        @Json(name = "code")
        val code: String = "",
        @Json(name = "name")
        val name: String = "",
        @Json(name = "symbol")
        val symbol: String = "",
        @Json(name = "exchange_rate")
        var exchangeRate: Float = 0.0F
)