package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import eu.szwiec.checkittravelkit.BR
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.util.NonNullLiveData
import eu.szwiec.checkittravelkit.util.format
import eu.szwiec.checkittravelkit.vo.Country
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class InfoViewModel(private val context: Context, private val preferences: Preferences, private val repository: CountryRepository) : ViewModel() {

    val isFavorite = NonNullLiveData(false)
    val countryName = NonNullLiveData("")
    val image = NonNullLiveData("https://www.dropbox.com/s/xlklf1nyqln3549/default.jpg?dl=1")
    val items: DiffObservableList<Any> = DiffObservableList(object : DiffObservableList.Callback<Any> {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return areContentsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is SimpleInfoViewModel && newItem is SimpleInfoViewModel) {
                return oldItem.text == newItem.text
            } else {
                return true
            }
        }
    })

    val itemBind = OnItemBindClass<Any>()
            .map(SimpleInfoViewModel::class.java, BR.item, R.layout.row_simple_info)
            .map(ElectricityInfoViewModel::class.java, BR.item, R.layout.row_electricity_info)
            .map(CallInfoViewModel::class.java, BR.item, R.layout.row_call_info)
            .map(VaccinationViewModel::class.java, BR.item, R.layout.row_vaccination_info)
            .map(Divider::class.java, ItemBinding.VAR_NONE, R.layout.info_divider);

    fun setupCountry(countryName: String, originCurrencyCode: String) {

        val country = repository.getCountry(countryName).value
        isFavorite.value = preferences.favorites.contains(countryName)
        if (country.imageUrl.isNotEmpty()) {
            image.value = country.imageUrl
        }

        val info = ObservableArrayList<Any>()

        info.add(SimpleInfoViewModel(formatTime(country), context.getDrawable(R.drawable.ic_time)))
        info.add(SimpleInfoViewModel(formatCurrency(country, originCurrencyCode), context.getDrawable(R.drawable.ic_currency)))
        if (country.visa != null) {
            info.add(SimpleInfoViewModel("${country.visa}", context.getDrawable(R.drawable.ic_visa)))
        }
        info.add(SimpleInfoViewModel(formatTapWater(country), context.getDrawable(R.drawable.ic_tap)))
        info.add(ElectricityInfoViewModel(formatElectricity(country), formatPlugs(country), context.getDrawable(R.drawable.ic_plug)))

        info.add(Divider())
        info.add(CallInfoViewModel("+${country.callingCode}", "${country.policeNumber}", "${country.ambulanceNumber}", context.getDrawable(R.drawable.ic_call)))
        info.add(Divider())

        if (country.vaccinations.isEmpty()) {
            info.add(SimpleInfoViewModel("You don't need vaccinactions", context.getDrawable(R.drawable.ic_vaccine)))
        } else {
            info.add(SimpleInfoViewModel("You may need vaccinations for:", context.getDrawable(R.drawable.ic_vaccine)))
            country.vaccinations.forEach { (key, value) -> info.add(VaccinationViewModel(key, value)) }
        }

        items.update(info)
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

    private fun formatPlugs(country: Country): List<PlugViewModel> {
        return country.plugs.map { getPlug(it) }
    }

    private fun formatElectricity(country: Country): String {
        return "${country.voltage}V ${country.frequency}Hz"
    }

    private fun formatTapWater(country: Country): String {
        return if (country.tapWater.isEmpty()) "Sorry, I don't have info about tap water." else "Tap water is ${country.tapWater}"
    }

    private fun formatTime(country: Country): String {
        return "It's ${getCurrentTime(country.timezone)} now"
    }

    private fun formatCurrency(country: Country, originCurrencyCode: String): String {
        val displayCurrencyName = if (country.currencyName.isNotEmpty()) country.currencyName else country.currencyCode
        val destinationCurrencyCode = country.currencyCode

        val currencyInfo1 = "${displayCurrencyName} is the currency"
        val currencyInfo2 = if (country.exchangeRate != 0.0F && originCurrencyCode.isNotEmpty() && destinationCurrencyCode.isNotEmpty())
            "1 ${originCurrencyCode} = ${country.exchangeRate.format(2)} ${destinationCurrencyCode}"
        else
            null

        return listOf(currencyInfo1, currencyInfo2).joinToString("\n")
    }

    private fun getCurrentTime(timezoneId: String): String {
        val timeZone = ZoneId.of(timezoneId)
        val zdt = ZonedDateTime.now(timeZone)
        return zdt.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    private fun getPlug(symbol: String): PlugViewModel {
        val resId = map.get(symbol)
        val icon = if (resId != null) context.getDrawable(resId) else context.getDrawable(R.drawable.ic_plug)
        return PlugViewModel(symbol, icon)
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