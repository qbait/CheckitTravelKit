package eu.szwiec.checkittravelkit.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository

class SplashViewModel(private val preferences: Preferences, private val repository: CountryRepository) : ViewModel() {

    val isFirstLaunch: LiveData<Boolean> = Transformations.map(preferences.isFirstLaunchLD()) { firstLaunch ->
        setup(firstLaunch)
        firstLaunch
    }

    private fun setup(firstLaunch: Boolean) {
        repository.setup()
        if (firstLaunch) {
            preferences.isFirstLaunch = false
        }
    }
}