package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.isValidFormat
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.vo.Currency
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.junit.Test


class InfoViewModelTest {

    val context = mock<Context>() {
        on { getString(R.string.x_is_the_currency) } doReturn "%1\$s is the currency"
        on { getString(R.string.currency_rate_info) } doReturn "1 %1\$s = %2${'$'}.2f %3\$s"
    }
    val preferences = mock<Preferences>()
    val repository = mock<CountryRepository>()
    val plugProvider = mock<PlugProvider>()
    val vm = InfoViewModel(context, preferences, repository, plugProvider)

    @Test
    fun getCurrentTimeWhenWrongTimezone() {
        val time = vm.getCurrentTime("")
        time shouldEqual ""
    }

    @Test
    fun getCurrentTimeWhenCorrectTimezone() {
        val pattern = "HH:mm"
        whenever(context.getString(R.string.time_pattern)).thenReturn(pattern)
        val time = vm.getCurrentTime("Europe/Warsaw")
        isValidFormat(pattern, time).shouldBeTrue()
    }

    @Test
    fun formatCurrency() {
        val originCurrencyCode = "PLN"
        val currency = Currency(code = "GBP", name = "British Pound", symbol = "£", exchangeRate = 4.84F)

        val expected = """
            British Pound is the currency
            1 PLN = 4.84 GBP
        """.trimIndent()

        vm.formatCurrency(currency, originCurrencyCode) shouldEqual expected
    }

    @Test
    fun formatCurrencyWhenNoName() {
        val originCurrencyCode = "PLN"
        val currency = Currency(code = "GBP", name = "", symbol = "£", exchangeRate = 4.84F)

        val expected = """
            GBP is the currency
            1 PLN = 4.84 GBP
        """.trimIndent()

        vm.formatCurrency(currency, originCurrencyCode) shouldEqual expected
    }

    @Test
    fun formatCurrencyWhenNoRate() {
        val originCurrencyCode = "PLN"
        val currency = Currency(code = "GBP", name = "British Pound", symbol = "£", exchangeRate = 0.0F)

        val expected = """
            British Pound is the currency
        """.trimIndent()

        vm.formatCurrency(currency, originCurrencyCode) shouldEqual expected
    }
}