package eu.szwiec.checkittravelkit.vo

import com.squareup.moshi.Json

data class Telephones(
        @Json(name = "prefix")
        val prefix: String = "",
        @Json(name = "police_number")
        val policeNumber: String = "",
        @Json(name = "ambulance_number")
        val ambulanceNumber: String = ""
)