package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import eu.szwiec.checkittravelkit.BR
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.util.NonNullLiveData
import eu.szwiec.checkittravelkit.vo.Country
import eu.szwiec.checkittravelkit.vo.Currency
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import org.threeten.bp.DateTimeException
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class InfoViewModel(private val context: Context, private val preferences: Preferences, private val repository: CountryRepository, private val plugProvider: PlugProvider) : ViewModel() {

    val isFavorite = NonNullLiveData(false)
    val countryName = NonNullLiveData("")
    val image = NonNullLiveData(context.getString(R.string.default_image_url))
    val items: DiffObservableList<Any> = DiffObservableList(object : DiffObservableList.Callback<Any> {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return areContentsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is SimpleInfo && newItem is SimpleInfo) {
                return oldItem.text == newItem.text
            } else {
                return true
            }
        }
    })

    val itemBind = OnItemBindClass<Any>()
            .map(SimpleInfo::class.java, BR.item, R.layout.row_simple_info)
            .map(ElectricityInfo::class.java, BR.item, R.layout.row_electricity_info)
            .map(CallInfo::class.java, BR.item, R.layout.row_call_info)
            .map(Vaccination::class.java, BR.item, R.layout.row_vaccination_info)
            .map(Divider::class.java, ItemBinding.VAR_NONE, R.layout.info_divider);

    fun toggleFavorite() {
        if (countryName.value.isNotEmpty()) {
            isFavorite.value = !isFavorite.value
            if (isFavorite.value) {
                preferences.addFavorite(countryName.value)
            } else {
                preferences.removeFavorite(countryName.value)
            }
        }
    }

    fun setupCountry(name: String, originCurrencyCode: String) {

        val country = repository.getCountry(name).value
        countryName.value = name
        isFavorite.value = preferences.favorites.contains(name)
        if (country.imageUrl.isNotEmpty()) {
            image.value = country.imageUrl
        }

        val newItems = getNewItems(country, originCurrencyCode)
        items.update(newItems)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private fun getNewItems(country: Country, originCurrencyCode: String): List<Any> {

        val items = ObservableArrayList<Any>()

        items.add(SimpleInfo(formatTime(country.timezone), context.getDrawable(R.drawable.ic_time)))
        items.add(SimpleInfo(formatCurrency(country.currency, originCurrencyCode), context.getDrawable(R.drawable.ic_currency)))
        if (country.visa.isNotEmpty()) {
            items.add(SimpleInfo(country.visa, context.getDrawable(R.drawable.ic_visa)))
        }
        items.add(SimpleInfo(formatTapWater(country.tapWater), context.getDrawable(R.drawable.ic_tap)))
        items.add(ElectricityInfo(context.getString(R.string.electricity, country.electricity.voltage, country.electricity.frequency), formatPlugs(country.electricity.plugs), context.getDrawable(R.drawable.ic_plug)))

        items.add(Divider())
        items.add(CallInfo(formatCallingCode(country.callInfo.callingCode), country.callInfo.policeNumber, country.callInfo.ambulanceNumber, context.getDrawable(R.drawable.ic_call)))
        items.add(Divider())

        if (country.vaccinations.isEmpty()) {
            items.add(SimpleInfo(context.getString(R.string.you_dont_need_vaccinations), context.getDrawable(R.drawable.ic_vaccine)))
        } else {
            items.add(SimpleInfo(context.getString(R.string.you_may_need_vaccinations_for), context.getDrawable(R.drawable.ic_vaccine)))
            country.vaccinations.forEach { (key, value) -> items.add(Vaccination(key, value)) }
        }

        return items
    }

    private fun formatCallingCode(callingCode: String): String {
        return if (callingCode.isNotEmpty()) context.getString(R.string.calling_code, callingCode) else ""
    }

    private fun formatTime(timezone: String): String {
        val time = getCurrentTime(timezone)
        return if (time.isNotEmpty()) context.getString(R.string.its_x_now, time) else context.getString(R.string.no_info_about_time)
    }

    private fun formatTapWater(basicInfo: String): String {
        return if (basicInfo.isNotEmpty()) context.getString(R.string.tap_water_is, basicInfo) else context.getString(R.string.no_info_about_tap_water)
    }

    private fun formatPlugs(plugs: List<String>): List<Plug> {
        return plugs.map { plugProvider.provide(it) }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun formatCurrency(currency: Currency, originCurrencyCode: String): String {
        val displayCurrencyName = if (currency.name.isNotEmpty()) currency.name else currency.code
        val destinationCurrencyCode = currency.code

        val currencyInfo1 = context.getString(R.string.x_is_the_currency).format(displayCurrencyName)
        val currencyInfo2 = if (currency.exchangeRate != 0.0F && originCurrencyCode.isNotEmpty() && destinationCurrencyCode.isNotEmpty())
            context.getString(R.string.currency_rate_info).format(originCurrencyCode, currency.exchangeRate, destinationCurrencyCode)
        else
            ""

        return listOf(currencyInfo1, currencyInfo2).filter { it.isNotEmpty() }.joinToString("\n")
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getCurrentTime(timezoneId: String): String {
        try {
            val timeZone = ZoneId.of(timezoneId)
            val zdt = ZonedDateTime.now(timeZone)
            val timePattern = context.getString(R.string.time_pattern)
            return zdt.toLocalTime().format(DateTimeFormatter.ofPattern(timePattern))
        } catch (exception: DateTimeException) {
            return ""
        }
    }
}