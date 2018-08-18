package eu.szwiec.checkittravelkit.ui.info

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.navigation.findNavController
import eu.szwiec.checkittravelkit.R

class TelephonesInfo(val prefix: String, val police: String, val ambulance: String, val icon: Drawable) {

    fun onClickPhoneNumber(v: TextView) {
        v.findNavController().navigate(R.id.next_action)
    }
}
