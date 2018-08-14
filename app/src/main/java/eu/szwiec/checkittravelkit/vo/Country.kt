package eu.szwiec.checkittravelkit.vo

import androidx.room.Entity
import com.squareup.moshi.Json

//import eu.szwiec.checkittravelkit.db.Converters

@Entity(primaryKeys = arrayOf("name"))
//@TypeConverters(Converters::class)
data class Country(
        @Json(name = "id")
        val id: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "timezone")
        val timezone: String = "",
        @Json(name = "voltage")
        val voltage: String = "",
        @Json(name = "frequency")
        val frequency: String = "",
        @Json(name = "plugs")
        val plugs: List<String> = emptyList(),
        @Json(name = "calling_code")
        val callingCode: String = "",
        @Json(name = "police_number")
        val policeNumber: String = "",
        @Json(name = "ambulance_number")
        val ambulanceNumber: String = "",
        @Json(name = "tap_water")
        val tapWater: String = "",
        @Json(name = "vaccinations")
        val vaccinations: Map<String, String> = emptyMap(),
        @Json(name = "currency_code")
        val currencyCode: String = "",
        @Json(name = "currency_name")
        val currencyName: String = "",
        @Json(name = "currency_symbol")
        val currencySymbol: String = "",
        @Json(name = "image_url")
        val imageUrl: String = "",
        @Json(name = "visa")
        var visa: String = "",
        @Json(name = "exchange_rate")
        var exchangeRate: Float = 0.0F
)