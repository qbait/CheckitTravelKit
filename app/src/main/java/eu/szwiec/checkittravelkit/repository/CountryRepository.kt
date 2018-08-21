package eu.szwiec.checkittravelkit.repository

import androidx.lifecycle.LiveData
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.data.Country
import eu.szwiec.checkittravelkit.repository.local.CountriesJsonReader
import eu.szwiec.checkittravelkit.repository.local.CountryDao
import eu.szwiec.checkittravelkit.util.AppExecutors

interface CountryRepository {
    fun setup()
    fun getCountryNames(): LiveData<List<String>>
    fun getCountry(name: String): LiveData<Country>
    fun getOriginCurrencyCode(): LiveData<String>
}

class CountryRepositoryImpl(
        private val appExecutors: AppExecutors,
        private val dao: CountryDao,
        private val jsonReader: CountriesJsonReader,
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

    override fun getOriginCurrencyCode(): LiveData<String> {
        return dao.findCurrencyCodeByName(preferences.origin)
    }

    override fun getCountry(name: String): LiveData<Country> {
        return dao.findByName(name)
    }

}
