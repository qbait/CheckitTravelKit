package eu.szwiec.checkittravelkit.vo

import com.squareup.moshi.Json

data class Textual(
        @Json(name = "class") val classX: String,
        @Json(name = "text") val text: List<String>
)