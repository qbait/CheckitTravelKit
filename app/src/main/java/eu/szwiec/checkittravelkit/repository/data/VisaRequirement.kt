package eu.szwiec.checkittravelkit.repository.data

import com.squareup.moshi.Json

data class VisaRequirement(
        @Json(name = "allowed_stay") val allowedStay: String,
        @Json(name = "citizenship_destination_ID_type") val citizenshipDestinationIDType: String,
        @Json(name = "requirement") val requirement: String,
        @Json(name = "type") val type: String,
        @Json(name = "textual") val textual: Textual
)