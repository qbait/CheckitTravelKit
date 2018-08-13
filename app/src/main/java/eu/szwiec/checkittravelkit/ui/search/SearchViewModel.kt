package eu.szwiec.checkittravelkit.ui.search

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.util.NonNullLiveData

enum class State {
    CHOOSE_ORIGIN, CHOOSE_DESTINATION
}

class SearchViewModel(private val resources: Resources, private val preferences: Preferences, private val repository: CountryRepository) : ViewModel() {

    val state = NonNullLiveData(State.CHOOSE_ORIGIN)
    val origin = NonNullLiveData("")
    val destination = NonNullLiveData("")
    val originHint = NonNullLiveData("")
    val countryNames = repository.getCountryNames()

    init {
        if (preferences.origin.isNotEmpty()) {
            origin.value = preferences.origin
            state.value = State.CHOOSE_DESTINATION
        }
    }

    fun editOrigin() {
        state.value = State.CHOOSE_ORIGIN
        originHint.value = resources.getString(R.string.where_are_you_from)
    }

    fun submitOrigin() {
        countryNames.value?.let {
            if (it.contains(origin.value)) {
                preferences.origin = origin.value
                state.value = State.CHOOSE_DESTINATION
                originHint.value = ""
            }
        }
    }

    fun submitDestination(name: String) {
        countryNames.value?.let {
            if (it.contains(destination.value)) {
                //navigationController.navigateToInfo(name)
                destination.value = ""
            }
        }
    }
}