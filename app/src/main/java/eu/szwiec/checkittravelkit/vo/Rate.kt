package eu.szwiec.checkittravelkit.vo

import com.squareup.moshi.Json

data class Rate(
        @Json(name = "val")
        val value: Float
)