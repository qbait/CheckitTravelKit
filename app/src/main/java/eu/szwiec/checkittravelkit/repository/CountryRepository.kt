package eu.szwiec.checkittravelkit.repository

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.data.Country
import eu.szwiec.checkittravelkit.repository.data.Rate
import eu.szwiec.checkittravelkit.repository.data.Visa
import eu.szwiec.checkittravelkit.repository.local.CountriesJsonReader
import eu.szwiec.checkittravelkit.repository.local.CountryDao
import eu.szwiec.checkittravelkit.repository.remote.*
import eu.szwiec.checkittravelkit.util.AppExecutors
import timber.log.Timber
import java.util.concurrent.TimeUnit

interface CountryRepository {
    fun setup()
    fun getCountryNames(): LiveData<List<String>>
    fun getCountry(name: String): LiveData<Country>
}

class CountryRepositoryImpl(
        private val appExecutors: AppExecutors,
        private val dao: CountryDao,
        private val jsonReader: CountriesJsonReader,
        private val sherpaService: SherpaService,
        private val currencyConverterService: CurrencyConverterService,
        private val preferences: Preferences,
        private val sherpaAuthorization: SherpaAuthorization
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

        val dbSource = dao.findByName(name)

        result.addSource(countrySource) { country ->
            Timber.d("onChanged country: name=%s, visa=%s, rate=%s", country.name, country.visa, country.currency.rate)
            result.removeSource(countrySource)
            result.value = country

            result.addSource(originSource) { origin ->
                result.removeSource(originSource)

                val currencyConverterSource = currencyConverterService.convert(currencyFromTo(origin, country), "y")
                val visaSource = sherpaService.visaRequirements(sherpaAuthorization.getBasic(), visaFromTo(origin, country))

                if (shouldFetchRate(country, origin)) {
                    result.addSource(currencyConverterSource) { response ->
                        result.removeSource(currencyConverterSource)
                        when (response) {
                            is ApiSuccessResponse -> {
                                val rate = Rate(response.body.value, origin.currency.code, System.currentTimeMillis())
                                val newCountry = country.update(rate)
                                appExecutors.diskIO().execute { dao.update(newCountry) }
                            }
                            is ApiErrorResponse -> {
                                Timber.w("Problem with fetching currency rate: %s", response.errorMessage)
                            }
                        }
                    }
                }

                if (shouldFetchVisa(country, origin)) {
                    result.addSource(visaSource) { response ->
                        result.removeSource(visaSource)
                        when (response) {
                            is ApiSuccessResponse -> {
                                val visa = Visa(response.body.info, origin.id, System.currentTimeMillis())
                                val newCountry = country.update(visa)
                                appExecutors.diskIO().execute { dao.update(newCountry) }
                            }
                            is ApiErrorResponse -> {
                                Timber.w("Problem with fetching visa info: %s", response.errorMessage)
                            }
                        }
                    }
                }
            }
        }

        result.addSource(dbSource) { country -> result.value = country }

        return result
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun shouldFetchVisa(country: Country, origin: Country): Boolean {
        val now = System.currentTimeMillis()
        val lastUpdate = country.visa.lastUpdate
        val oneMonth = TimeUnit.DAYS.toMillis(30)

        val visaOrigin = country.visa.fromCountryId
        val currentOrigin = origin.id

        return now - lastUpdate > oneMonth || visaOrigin != currentOrigin
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun shouldFetchRate(country: Country, origin: Country): Boolean {
        val now = System.currentTimeMillis()
        val lastUpdate = country.currency.rate.lastUpdate
        val oneDay = TimeUnit.DAYS.toMillis(1)

        val rateOrigin = country.currency.rate.fromCurrencyCode
        val currentOrigin = origin.currency.code

        return now - lastUpdate > oneDay || rateOrigin != currentOrigin
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun currencyFromTo(from: Country, to: Country): String {
        return "${from.currency.code}_${to.currency.code}"
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun visaFromTo(from: Country, to: Country): String {
        return "${from.id}-${to.id}"
    }

}
