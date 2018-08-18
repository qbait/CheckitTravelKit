package eu.szwiec.checkittravelkit.repository

import androidx.lifecycle.LiveData
import eu.szwiec.checkittravelkit.repository.data.Country

interface CountryRepository {
    fun getCountryNames(): LiveData<List<String>>
    fun getCountry(name: String): LiveData<Country>
    fun getOriginCurrencyCode(): LiveData<String>
}

class CountryRepositoryImpl : CountryRepository {
    override fun getCountryNames(): LiveData<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCountry(name: String): LiveData<Country> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOriginCurrencyCode(): LiveData<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
