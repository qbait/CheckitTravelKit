package eu.szwiec.checkittravelkit.repository

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eu.szwiec.checkittravelkit.util.NonNullLiveData
import eu.szwiec.checkittravelkit.vo.CallInfo
import eu.szwiec.checkittravelkit.vo.Country
import eu.szwiec.checkittravelkit.vo.Currency
import eu.szwiec.checkittravelkit.vo.Electricity

class CountryRepository {
    fun getCountryNames(): LiveData<List<String>> {
        return NonNullLiveData(listOf("Poland", "UK"))
    }

    fun getCountry(name: String): LiveData<Country> {
        val countryLD = MutableLiveData<Country>()

        val country = Country(
                id = "PL",
                name = "Poland",
                timezone = "Europe/Warsaw",
                tapWater = "safe",
                vaccinations = mapOf("Hepatitis B" to "The vaccination advice is personal. Consult a qualified medical professional to determine whether vaccination is useful for you"),
                imageUrl = "https://www.dropbox.com/s/5b06v8pgg4ifxy0/poland.jpg?dl=1",
                visa = "Not required",
                electricity = Electricity(
                        voltage = "230",
                        frequency = "50",
                        plugs = listOf("C", "E")
                ),
                callInfo = CallInfo(
                        policeNumber = "112",
                        ambulanceNumber = "112",
                        callingCode = "48"
                ),
                currency = Currency(
                        code = "PLN",
                        name = "Polish zloty",
                        symbol = "zł",
                        exchangeRate = 0.2F
                )
        )

        Handler().postDelayed({ countryLD.postValue(country) }, 500)

        return countryLD
    }

    fun getOriginCurrencyCode(): LiveData<String> {
        val code = MutableLiveData<String>()
        code.postValue("GBP")
        return code
    }
}
