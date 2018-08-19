package eu.szwiec.checkittravelkit.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.navigation.findNavController
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.ui.search.SearchFragmentDirections
import java.net.URLEncoder

fun navigateToSearch(view: View) {
    view.findNavController().navigate(R.id.next_action)
}

fun navigateToInfo(view: View, countryName: String) {
    val action = SearchFragmentDirections.nextAction().setCountryId(countryName)
    view.findNavController().navigate(action)
}

fun navigateToDialer(context: Context, number: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number))
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}

fun navigateToGoogleResults(context: Context, query: String) {
    val escapedQuery = URLEncoder.encode(query, "UTF-8")
    val uri = Uri.parse("http://www.google.com/#q=$escapedQuery")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}