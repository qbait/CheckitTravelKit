package eu.szwiec.checkittravelkit.ui.search

import android.view.View
import androidx.navigation.findNavController

class FavoriteCountry(val name: String) {

    fun onClick(v: View): Boolean {
        navigateToInfo(name, v)
        return true
    }

    private fun navigateToInfo(countryName: String, view: View) {
        val action = SearchFragmentDirections.nextAction().setCountryId(countryName)
        view.findNavController().navigate(action)
    }
}
