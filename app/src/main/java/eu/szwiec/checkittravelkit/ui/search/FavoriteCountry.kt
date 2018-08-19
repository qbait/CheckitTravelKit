package eu.szwiec.checkittravelkit.ui.search

import android.view.View
import eu.szwiec.checkittravelkit.ui.common.navigateToInfo

class FavoriteCountry(val name: String) {

    fun onClick(v: View): Boolean {
        navigateToInfo(v, name)
        return true
    }
}
