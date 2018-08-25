package eu.szwiec.checkittravelkit.repository.remote

import com.squareup.moshi.FromJson
import eu.szwiec.checkittravelkit.repository.data.Visa
import eu.szwiec.checkittravelkit.vo.VisaRequirement
import timber.log.Timber

class VisaAdapter {

    @FromJson
    fun fromJson(requirements: List<VisaRequirement>): Visa {
        try {
            return Visa(info = requirements[0].textual.text[1])
        } catch (e: Exception) {
            Timber.e("Parsing Visa requirements error: %s", e)
            return Visa(info = "")
        }
    }

}