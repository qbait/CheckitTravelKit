package eu.szwiec.checkittravelkit.repository.data

import com.squareup.moshi.Json

data class Rate(
        @Json(name = "val")
        val value: Float
)