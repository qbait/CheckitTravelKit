package eu.szwiec.checkittravelkit.repository

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.ui.info.InfoViewModel
import eu.szwiec.checkittravelkit.ui.info.PlugProvider
import eu.szwiec.checkittravelkit.util.InstantAppExecutors
import org.junit.Test

class CountryRepositoryImplTest {

    private val context: Context by inject()
    private val preferences = mock<Preferences>()
    private val plugProvider: PlugProvider by inject()
    private val repository = CountryRepositoryImpl(context, InstantAppExecutors(), repository, plugProvider)

    @Test
    fun getCountryNames() {
    }

    @Test
    fun getCountry() {
    }
}