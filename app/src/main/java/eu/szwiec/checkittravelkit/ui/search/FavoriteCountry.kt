package eu.szwiec.checkittravelkit.ui.search

import android.view.View
import androidx.navigation.findNavController
import eu.szwiec.checkittravelkit.R

class FavoriteCountry(val name: String) {

    fun onClick(v: View): Boolean {
        v.findNavController().navigate(R.id.next_action)
        return true
    }
}
