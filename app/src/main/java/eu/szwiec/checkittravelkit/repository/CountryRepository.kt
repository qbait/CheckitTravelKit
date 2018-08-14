package eu.szwiec.checkittravelkit.repository

import androidx.lifecycle.LiveData
import eu.szwiec.checkittravelkit.util.NonNullLiveData
import eu.szwiec.checkittravelkit.vo.Country

class CountryRepository {
    fun getCountryNames(): LiveData<List<String>> {
        return NonNullLiveData(listOf("Poland", "UK"))
    }

    fun getCountry(name: String): NonNullLiveData<Country> {
        val country = Country(
                id = "PL",
                name = "Poland",
                timezone = "Europe/Warsaw",
                voltage = "230",
                frequency = "50",
                plugs = listOf("C", "E"),
                policeNumber = "112",
                ambulanceNumber = "112",
                tapWater = "safe",
                vaccinations = mapOf("Hepatitis B" to "The vaccination advice is personal. Consult a qualified medical professional to determine whether vaccination is useful for you"),
                currencyCode = "PLN",
                currencyName = "Polish zloty",
                currencySymbol = "zł",
                imageUrl = "https://www.dropbox.com/s/5b06v8pgg4ifxy0/poland.jpg?dl=1",
                visa = "Not required",
                exchangeRate = 0.2F

        )
        return NonNullLiveData(country)
    }
}
