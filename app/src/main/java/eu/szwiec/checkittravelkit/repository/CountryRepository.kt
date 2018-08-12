package eu.szwiec.checkittravelkit.repository

import androidx.lifecycle.LiveData
import eu.szwiec.checkittravelkit.util.NonNullLiveData

class CountryRepository {
    fun getCountryNames(): LiveData<List<String>> {
        return NonNullLiveData(listOf("Poland", "UK"))
    }
}
