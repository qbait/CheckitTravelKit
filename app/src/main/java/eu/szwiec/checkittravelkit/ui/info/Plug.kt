package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import java.net.URLEncoder

class Plug(val symbol: String, val icon: Drawable) {

    fun showInfo(v: View, plug: Plug) {
        val title = "plug type ${plug.symbol}"
        navigateToGoogleResults(v.context, title)
    }

    fun navigateToGoogleResults(context: Context, query: String) {
        val escapedQuery = URLEncoder.encode(query, "UTF-8")
        val uri = Uri.parse("http://www.google.com/#q=$escapedQuery")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}