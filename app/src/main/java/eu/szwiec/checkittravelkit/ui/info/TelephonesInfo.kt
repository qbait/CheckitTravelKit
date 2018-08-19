package eu.szwiec.checkittravelkit.ui.info

import android.graphics.drawable.Drawable
import android.widget.TextView
import eu.szwiec.checkittravelkit.ui.common.navigateToDialer

class TelephonesInfo(val prefix: String, val police: String, val ambulance: String, val icon: Drawable) {

    fun onClickPhoneNumber(v: TextView) {
        navigateToDialer(v.context, v.text.toString())
    }
}
