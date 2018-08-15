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
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class InfoViewModel(private val context: Context, private val preferences: Preferences, private val repository: CountryRepository) : ViewModel() {

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

    fun setupCountry(countryName: String, originCurrencyCode: String) {

        val country = repository.getCountry(countryName).value
        isFavorite.value = preferences.favorites.contains(countryName)
        if (country.imageUrl.isNotEmpty()) {
            image.value = country.imageUrl
        }

        val info = ObservableArrayList<Any>()

        info.add(SimpleInfo(formatTime(country), context.getDrawable(R.drawable.ic_time)))
        info.add(SimpleInfo(formatCurrency(country.currency, originCurrencyCode), context.getDrawable(R.drawable.ic_currency)))
        if (country.visa != null) {
            info.add(SimpleInfo("${country.visa}", context.getDrawable(R.drawable.ic_visa)))
        }
        info.add(SimpleInfo(formatTapWater(country), context.getDrawable(R.drawable.ic_tap)))
        info.add(ElectricityInfo(context.getString(R.string.electricity, country.electricity.voltage, country.electricity.frequency), formatPlugs(country.electricity.plugs), context.getDrawable(R.drawable.ic_plug)))

        info.add(Divider())
        info.add(getCallInfo(country.callInfo))
        info.add(Divider())

        if (country.vaccinations.isEmpty()) {
            info.add(SimpleInfo(context.getString(R.string.you_dont_need_vaccinations), context.getDrawable(R.drawable.ic_vaccine)))
        } else {
            info.add(SimpleInfo(context.getString(R.string.you_may_need_vaccinations_for), context.getDrawable(R.drawable.ic_vaccine)))
            country.vaccinations.forEach { (key, value) -> info.add(Vaccination(key, value)) }
        }

        items.update(info)
    }

    private fun getCallInfo(callInfo: eu.szwiec.checkittravelkit.vo.CallInfo): Any {
        return CallInfo(context.getString(R.string.calling_code, callInfo.callingCode), "${callInfo.policeNumber}", "${callInfo.ambulanceNumber}", context.getDrawable(R.drawable.ic_call))
    }

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

    private fun formatPlugs(plugs: List<String>): List<Plug> {
        return plugs.map { getPlug(it) }
    }

    private fun formatTapWater(country: Country): String {
        return if (country.tapWater.isEmpty()) context.getString(R.string.no_info_about_tap_water) else context.getString(R.string.tap_water_is, country.tapWater)
    }

    private fun formatTime(country: Country): String {
        return context.getString(R.string.its_x_now, getCurrentTime(country.timezone))
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun formatCurrency(currency: Currency, originCurrencyCode: String): String {
        val displayCurrencyName = if (currency.name.isNotEmpty()) currency.name else currency.code
        val destinationCurrencyCode = currency.code

        val currencyInfo1 = context.getString(R.string.x_is_the_currency, displayCurrencyName)
        val currencyInfo2 = if (currency.exchangeRate != 0.0F && originCurrencyCode.isNotEmpty() && destinationCurrencyCode.isNotEmpty())
            context.getString(R.string.currency_rate_info, originCurrencyCode, currency.exchangeRate, destinationCurrencyCode)
        else
            null

        return listOf(currencyInfo1, currencyInfo2).joinToString("\n")
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getCurrentTime(timezoneId: String): String {
        val timeZone = ZoneId.of(timezoneId)
        val zdt = ZonedDateTime.now(timeZone)
        val timePattern = context.getString(R.string.time_pattern)
        return zdt.toLocalTime().format(DateTimeFormatter.ofPattern(timePattern))
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getPlug(symbol: String): Plug {
        val resId = map.get(symbol)
        val icon = if (resId != null) context.getDrawable(resId) else context.getDrawable(R.drawable.ic_plug)
        return Plug(symbol, icon)
    }

    private val map = mapOf(
            "A" to R.drawable.plug_a,
            "B" to R.drawable.plug_b,
            "C" to R.drawable.plug_c,
            "D" to R.drawable.plug_d,
            "E" to R.drawable.plug_e,
            "F" to R.drawable.plug_f,
            "G" to R.drawable.plug_g,
            "H" to R.drawable.plug_h,
            "I" to R.drawable.plug_i,
            "J" to R.drawable.plug_j,
            "K" to R.drawable.plug_k,
            "L" to R.drawable.plug_l
    )
}