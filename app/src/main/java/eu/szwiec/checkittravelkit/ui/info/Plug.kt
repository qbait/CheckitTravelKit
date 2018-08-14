package eu.szwiec.checkittravelkit.ui.info

import android.graphics.drawable.Drawable
import android.view.View
import androidx.navigation.findNavController
import eu.szwiec.checkittravelkit.R

class Plug(val symbol: String, val icon: Drawable) {

    fun showInfo(v: View, plug: Plug) {
        val title = "plug type ${plug.symbol}"
        v.findNavController().navigate(R.id.next_action)
    }
}