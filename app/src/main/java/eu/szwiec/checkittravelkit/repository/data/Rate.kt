package eu.szwiec.checkittravelkit.repository.data

import com.squareup.moshi.Json

data class Rate(
        @Json(name = "value")
        val value: Float,
        @Json(name = "from_symbol")
        val fromSymbol: String
)