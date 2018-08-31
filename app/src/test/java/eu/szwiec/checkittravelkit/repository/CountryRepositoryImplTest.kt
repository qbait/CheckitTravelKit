package eu.szwiec.checkittravelkit.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.local.CountriesJsonReader
import eu.szwiec.checkittravelkit.repository.local.CountryDao
import eu.szwiec.checkittravelkit.repository.remote.CurrencyConverterService
import eu.szwiec.checkittravelkit.repository.remote.SherpaAuthorization
import eu.szwiec.checkittravelkit.repository.remote.SherpaService
import eu.szwiec.checkittravelkit.util.InstantAppExecutors
import org.junit.Rule
import org.junit.Test

class CountryRepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dao = mock<CountryDao>()
    private val jsonReader = mock<CountriesJsonReader>()
    private val sherpaService = mock<SherpaService>()
    private val currencyConverterService = mock<CurrencyConverterService>()
    private val preferences = mock<Preferences>()
    private val sherpaAuthorization = mock<SherpaAuthorization>()

    private val repository = CountryRepositoryImpl(InstantAppExecutors(), dao, jsonReader, sherpaService, currencyConverterService, preferences, sherpaAuthorization)

    @Test
    fun getCountryNames() {
        repository.getCountryNames()
        verify(dao).getNames()
    }

    @Test
    fun getCountry() {
//        val countryObserver = mock<Observer<Country>>()
        whenever(preferences.origin).thenReturn("Poland")
//        whenever(dao.findByName("Poland")).thenReturn(NonNullLiveData(poland))
//        whenever(dao.findByName("Thailand")).thenReturn(NonNullLiveData(thailand))
//        whenever(currencyConverterService.convert(any(), any())).thenReturn(NonNullLiveData(ApiSuccessResponse(Rate(0.22, "PLN", 0))))
//        whenever(sherpaService.visaRequirements(any(), any())).thenReturn(NonNullLiveData(ApiSuccessResponse(Visa("Visa required"))))
//        TODO better test

        repository.getCountry("Thailand")
        verify(dao).findByName("Poland")
        verify(dao, times(2)).findByName("Thailand")
    }



}