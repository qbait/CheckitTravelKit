package eu.szwiec.checkittravelkit.ui.info

import android.graphics.drawable.Drawable
import android.view.View
import eu.szwiec.checkittravelkit.ui.common.navigateToGoogleResults

class Plug(val symbol: String, val icon: Drawable) {

    fun showInfo(v: View, plug: Plug) {
        val title = "plug type ${plug.symbol}"
        navigateToGoogleResults(v.context, title)
    }
}