package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.isValidFormat
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class InfoViewModelTest {

    lateinit var infoViewModel: InfoViewModel

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockPreferences: Preferences

    @Mock
    lateinit var mockRepository: CountryRepository

    @Mock
    lateinit var mockPlugProvider: PlugProvider

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        infoViewModel = InfoViewModel(mockContext, mockPreferences, mockRepository, mockPlugProvider)
    }

    @Test
    fun getCurrentTimeWhenWrongTimezone() {
        val time = infoViewModel.getCurrentTime("")
        assertEquals("", time)
    }

    @Test
    fun getCurrentTimeWhenCorrectTimezone() {
        val pattern = "HH:mm"
        `when`(mockContext.getString(R.string.time_pattern)).thenReturn(pattern)
        val time = infoViewModel.getCurrentTime("Europe/Warsaw")
        assertTrue(isValidFormat(pattern, time))
    }
}