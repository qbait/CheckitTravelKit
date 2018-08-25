package eu.szwiec.checkittravelkit.repository.data

import androidx.room.Embedded
import com.squareup.moshi.Json

data class Currency(
        @Json(name = "code")
        val code: String = "",

        @Json(name = "name")
        val name: String = "",

        @Json(name = "symbol")
        val symbol: String = "",

        @Embedded(prefix = "rate_")
        @Json(name = "rate")
        var rate: Rate = Rate()
)