package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.isValidFormat
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.vo.Currency
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

    @Test
    fun formatCurrency() {
        `when`(mockContext.getString(R.string.x_is_the_currency)).thenReturn("%1\$s is the currency")
        `when`(mockContext.getString(R.string.currency_rate_info)).thenReturn("1 %1\$s = %2${'$'}.2f %3\$s")

        val originCurrencyCode = "PLN"
        val currency = Currency(code = "GBP", name = "British Pound", symbol = "£", exchangeRate = 4.84F)

        val expected = """
            British Pound is the currency
            1 PLN = 4.84 GBP
        """.trimIndent()

        val actual = infoViewModel.formatCurrency(currency, originCurrencyCode)

        assertEquals(expected, actual)
    }

    @Test
    fun formatCurrencyWhenNoName() {
        `when`(mockContext.getString(R.string.x_is_the_currency)).thenReturn("%1\$s is the currency")
        `when`(mockContext.getString(R.string.currency_rate_info)).thenReturn("1 %1\$s = %2${'$'}.2f %3\$s")

        val originCurrencyCode = "PLN"
        val currency = Currency(code = "GBP", name = "", symbol = "£", exchangeRate = 4.84F)

        val expected = """
            GBP is the currency
            1 PLN = 4.84 GBP
        """.trimIndent()

        val actual = infoViewModel.formatCurrency(currency, originCurrencyCode)

        assertEquals(expected, actual)
    }

    @Test
    fun formatCurrencyWhenNoRate() {
        `when`(mockContext.getString(R.string.x_is_the_currency)).thenReturn("%1\$s is the currency")
        `when`(mockContext.getString(R.string.currency_rate_info)).thenReturn("1 %1\$s = %2${'$'}.2f %3\$s")

        val originCurrencyCode = "PLN"
        val currency = Currency(code = "GBP", name = "British Pound", symbol = "£", exchangeRate = 0.0F)

        val expected = """
            British Pound is the currency
        """.trimIndent()

        val actual = infoViewModel.formatCurrency(currency, originCurrencyCode)

        assertEquals(expected, actual)
    }
}