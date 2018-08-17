package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import eu.szwiec.checkittravelkit.isValidFormat
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.vo.Currency
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class InfoViewModelTest : AutoCloseKoinTest() {

    val context: Context by inject()
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