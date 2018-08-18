package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.TextView

class TelephonesInfo(val prefix: String, val police: String, val ambulance: String, val icon: Drawable) {

    fun onClickPhoneNumber(v: TextView) {
        navigateToDialer(v.context, v.text.toString())
    }

    private fun navigateToDialer(context: Context, number: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number))
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
