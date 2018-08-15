package eu.szwiec.checkittravelkit.vo

import com.squareup.moshi.Json

data class Electricity(
        @Json(name = "voltage")
        val voltage: String = "",
        @Json(name = "frequency")
        val frequency: String = "",
        @Json(name = "plugs")
        val plugs: List<String> = emptyList()
)