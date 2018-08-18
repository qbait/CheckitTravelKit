package eu.szwiec.checkittravelkit.repository.data

import com.squareup.moshi.Json

data class Electricity(
        @Json(name = "voltage")
        val voltage: String = "",
        @Json(name = "frequency")
        val frequency: String = "",
        @Json(name = "plugs")
        val plugs: List<String> = emptyList()
)