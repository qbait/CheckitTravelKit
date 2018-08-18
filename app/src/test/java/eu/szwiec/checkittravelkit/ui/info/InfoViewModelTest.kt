package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.isValidFormat
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.repository.data.Country
import eu.szwiec.checkittravelkit.repository.data.Currency
import eu.szwiec.checkittravelkit.repository.data.Electricity
import eu.szwiec.checkittravelkit.repository.data.Telephones
import eu.szwiec.checkittravelkit.resId
import org.amshove.kluent.*
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
    val plugProvider: PlugProvider by inject()
    val vm = InfoViewModel(context, preferences, repository, plugProvider)

    @Test
    fun setup() {
        val isFavoriteObserver = mock<Observer<Boolean>>()
        vm.isFavorite.observeForever(isFavoriteObserver)

        vm.setup(Pair(poland, "GBP"))

        vm.countryName.value shouldEqual poland.name
        vm.image.value shouldEqual poland.imageUrl
        verify(isFavoriteObserver).onChanged(any())
        vm.items.size shouldEqual polandItemsSize
    }

    @Test
    fun setupWhenIsFavorite() {
        whenever(preferences.favorites).thenReturn(setOf("Poland"))
        vm.setupFavorite("Poland")
        vm.isFavorite.value shouldEqual true
    }

    @Test
    fun setupWhenIsNotFavorite() {
        whenever(preferences.favorites).thenReturn(emptySet())
        vm.setupFavorite("Poland")
        vm.isFavorite.value shouldEqual false
    }

    @Test
    fun setupImage() {
        vm.setupImage(poland.imageUrl)
        vm.image.value shouldEqual poland.imageUrl
    }

    @Test
    fun setupDefaultImageWhenWrongUrl() {
        vm.setupImage("xxx")
        vm.image.value shouldEqual context.getString(R.string.default_image_url)

        vm.setupImage("")
        vm.image.value shouldEqual context.getString(R.string.default_image_url)
    }

    @Test
    fun toggleFavoriteDoNotUpdatePreferencesWhenNotReady() {
        vm.countryName.value = ""
        vm.toggleFavorite()
        verify(preferences, never()).removeFavorite(any())
    }

    @Test
    fun removeFavorite() {
        vm.countryName.value = "Poland"
        vm.isFavorite.value = true

        vm.toggleFavorite()
        verify(preferences).removeFavorite("Poland")
    }

    @Test
    fun addFavorite() {
        vm.countryName.value = "Poland"
        vm.isFavorite.value = false

        vm.toggleFavorite()
        verify(preferences).addFavorite("Poland")
    }

    @Test
    fun infoItemsAreShownCorrectly() {

        val items = vm.getNewItems(poland, "GBP")

        items.size shouldEqual polandItemsSize

        val time = items.get(0) as SimpleInfo
        val currency = items.get(1) as SimpleInfo
        val visa = items.get(2) as SimpleInfo
        val tapWater = items.get(3) as SimpleInfo
        val electricity = items.get(4) as ElectricityInfo

        items.get(5).shouldBeInstanceOf(Divider::class)

        val telephones = items.get(6) as TelephonesInfo

        items.get(7).shouldBeInstanceOf(Divider::class)

        val vaccination = items.get(8) as SimpleInfo

        time.icon.resId shouldEqual R.drawable.ic_time
        currency.icon.resId shouldEqual R.drawable.ic_currency
        visa.icon.resId shouldEqual R.drawable.ic_visa
        tapWater.icon.resId shouldEqual R.drawable.ic_tap
        electricity.icon.resId shouldEqual R.drawable.ic_plug
        telephones.icon.resId shouldEqual R.drawable.ic_call
        vaccination.icon.resId shouldEqual R.drawable.ic_vaccine
    }

    @Test
    fun getVaccinationItemsWhenEmpty() {
        val items = vm.getVaccinationItems(emptyMap())
        val noVaccinations = items.first() as SimpleInfo
        noVaccinations.text shouldEqual context.getString(R.string.you_dont_need_vaccinations)
        noVaccinations.icon.resId shouldEqual R.drawable.ic_vaccine
    }

    @Test
    fun getVaccinationItemsWhenNotEmpty() {
        val map = mapOf("Hepatitis B" to "The vaccination advice is personal. Consult a qualified medical professional to determine whether vaccination is useful for you")
        val vaccinations = vm.getVaccinationItems(map)

        val info = vaccinations.get(0) as SimpleInfo
        info.text shouldEqual context.getString(R.string.you_may_need_vaccinations_for)
        info.icon.resId shouldEqual R.drawable.ic_vaccine

        val vaccination = vaccinations.get(1) as VaccinationInfo
        vaccination.title shouldEqual "Hepatitis B"
        vaccination.description shouldEqual "The vaccination advice is personal. Consult a qualified medical professional to determine whether vaccination is useful for you"
    }

    @Test
    fun formatVisa() {
        vm.formatVisa("You need visa") shouldEqual "You need visa"
        vm.formatVisa("") shouldEqual context.getString(R.string.no_info_about_visa)
    }

    @Test
    fun formatCallingCode() {
        vm.formatCallingCode("") shouldEqual ""
        vm.formatCallingCode("xxx") shouldEqual ""
        vm.formatCallingCode("48") shouldEqual "+48"
    }

    @Test
    fun formatTime() {
        vm.formatTime("xxx") shouldEqual context.getString(R.string.no_info_about_time)
        vm.formatTime("") shouldEqual context.getString(R.string.no_info_about_time)
        vm.formatTime("Europe/Warsaw") shouldStartWith "It's" shouldEndWith "now"
    }

    @Test
    fun formatTapWater() {
        vm.formatTapWater("safe") shouldEqual context.getString(R.string.tap_water_is, "safe")
        vm.formatTapWater("not safe") shouldEqual context.getString(R.string.tap_water_is, "not safe")
        vm.formatTapWater("") shouldEqual context.getString(R.string.no_info_about_tap_water)
        vm.formatTapWater("xxx") shouldEqual context.getString(R.string.no_info_about_tap_water)
    }

    @Test
    fun formatPlugs() {
        val plugs = vm.formatPlugs(listOf("A", "L"))

        plugs[0].icon.resId shouldEqual R.drawable.plug_a
        plugs[0].symbol shouldEqual "A"
        plugs[1].icon.resId shouldEqual R.drawable.plug_l
        plugs[1].symbol shouldEqual "L"
    }

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

val poland = Country(
        id = "PL",
        name = "Poland",
        timezone = "Europe/Warsaw",
        tapWater = "safe",
        vaccinations = mapOf("Hepatitis B" to "The vaccination advice is personal. Consult a qualified medical professional to determine whether vaccination is useful for you"),
        imageUrl = "https://www.dropbox.com/s/5b06v8pgg4ifxy0/poland.jpg?dl=1",
        visa = "Not required",
        electricity = Electricity(
                voltage = "230",
                frequency = "50",
                plugs = listOf("C", "E")
        ),
        telephones = Telephones(
                policeNumber = "112",
                ambulanceNumber = "112",
                prefix = "48"
        ),
        currency = Currency(
                code = "PLN",
                name = "Polish zloty",
                symbol = "zł",
                exchangeRate = 0.2F
        )
)

const val polandItemsSize = 10