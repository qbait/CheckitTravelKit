package eu.szwiec.checkittravelkit.repository.remote

import android.content.Context
import android.util.Base64
import androidx.annotation.VisibleForTesting
import eu.szwiec.checkittravelkit.R

class SherpaAuthorization(private val context: Context) {

    fun getBasic(): String {
        val username = context.getString(R.string.sherpa_username)
        val password = context.getString(R.string.sherpa_password)

        return generate(username, password)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun generate(username: String, password: String): String {
        val credentials = "$username:$password"
        val basic = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        return basic
    }
}