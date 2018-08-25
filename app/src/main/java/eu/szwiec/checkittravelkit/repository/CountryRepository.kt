package eu.szwiec.checkittravelkit.repository

import android.content.Context
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.data.Country
import eu.szwiec.checkittravelkit.repository.local.CountriesJsonReader
import eu.szwiec.checkittravelkit.repository.local.CountryDao
import eu.szwiec.checkittravelkit.repository.remote.*
import eu.szwiec.checkittravelkit.util.AppExecutors

interface CountryRepository {
    fun setup()
    fun getCountryNames(): LiveData<List<String>>
    fun getCountry(name: String): LiveData<Country>
}

class CountryRepositoryImpl(
        private val context: Context,
        private val appExecutors: AppExecutors,
        private val dao: CountryDao,
        private val jsonReader: CountriesJsonReader,
        private val sherpaService: SherpaService,
        private val currencyConverterService: CurrencyConverterService,
        private val preferences: Preferences
) : CountryRepository {

    override fun setup() {
        appExecutors.diskIO().execute {
            if (dao.countCountries() == 0) {
                val countries = jsonReader.getCountries()
                dao.bulkInsert(countries)
            }
        }
    }

    override fun getCountryNames(): LiveData<List<String>> {
        return dao.getNames()
    }

    override fun getCountry(name: String): LiveData<Country> {
        val result = MediatorLiveData<Country>()

        val originSource = dao.findByName(preferences.origin)
        val countrySource = dao.findByName(name)
        val countrySource2 = dao.findByName(name)

        result.addSource(countrySource) { country ->
            result.removeSource(countrySource)
            result.value = country

            result.addSource(originSource) { origin ->
                result.removeSource(originSource)

                val currencyConverterSource = currencyConverterService.convert(currencyFromTo(origin, country), "y")
                val visaSource = sherpaService.visaRequirements(auth(), visaFromTo(origin, country))

                result.addSource(currencyConverterSource) { response ->
                    result.removeSource(currencyConverterSource)
                    when (response) {
                        is ApiSuccessResponse -> {
                            val newCountry = country.update(response.body)
                            appExecutors.diskIO().execute { dao.update(newCountry) }
                        }
                        is ApiErrorResponse -> { }
                    }
                }

                result.addSource(visaSource) { response ->
                    result.removeSource(currencyConverterSource)
                    when (response) {
                        is ApiSuccessResponse -> {
                            val newCountry = country.update(response.body)
                            appExecutors.diskIO().execute { dao.update(newCountry) }
                        }
                        is ApiErrorResponse -> { }
                    }
                }

            }
        }

        result.addSource(countrySource2) { country ->
            if( country.currency.rate.lastUpdate != 0L && country.visa.lastUpdate != 0L ) {
                result.value = country
                result.removeSource(countrySource)
            }
        }

        return result
    }

    private fun currencyFromTo(from: Country, to: Country): String {
        return "${from.currency}_${to.currency}"
    }

    private fun visaFromTo(from: Country, to: Country): String {
        return "${from.id}-${to.id}"
    }

    private fun auth(): String {
        val username = context.getString(R.string.sherpa_username)
        val password = context.getString(R.string.sherpa_password)
        val credentials = "$username:$password"
        val basic = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        return basic
    }

}
