package eu.szwiec.checkittravelkit.repository.remote

import com.squareup.moshi.FromJson
import eu.szwiec.checkittravelkit.repository.data.Rate

class RateAdapter {

    @FromJson
    fun fromJson(json: Any): Rate {
        val map1 = json as Map<String, Any>
        val map2 = map1.map { it.value }[0] as Map<String, Float>
        val rate = map2.get("val") as Double
        return Rate(value = rate)
    }
}