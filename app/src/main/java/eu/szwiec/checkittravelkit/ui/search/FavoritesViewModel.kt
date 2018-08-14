package eu.szwiec.checkittravelkit.ui.search

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import eu.szwiec.checkittravelkit.BR
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.prefs.Preferences
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

class FavoritesViewModel(private val application: Application, private val preferences: Preferences) : ViewModel() {

    init {
        preferences.addFavorite("Poland")
        preferences.addFavorite("France")
    }

    val itemBind = OnItemBindClass<Any>()
            .map(FavoriteCountryViewModel::class.java, BR.item, R.layout.row_fave_country)
            .map(Drawable::class.java, BR.item, R.layout.row_fave_icon)

    val items: DiffObservableList<Any> = DiffObservableList(object : DiffObservableList.Callback<Any> {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return areContentsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is FavoriteCountryViewModel && newItem is FavoriteCountryViewModel) {
                return oldItem.name == newItem.name
            } else {
                return true
            }
        }
    })

    val favorites = Transformations.map(preferences.getFavoritesLD()) {
        refreshList()
    }

    fun refreshList() {
        val headerFooterItems = ObservableArrayList<Any>()
        val favorites = preferences.favorites.map { FavoriteCountryViewModel(it) }.sortedBy { it.name }

        if (favorites.isNotEmpty()) {
            headerFooterItems.add(application.getDrawable(R.drawable.ic_favorite_border_white_24dp))
            headerFooterItems.addAll(favorites)
            headerFooterItems.add((application.getDrawable(android.R.color.transparent)))
        }

        items.update(headerFooterItems)
    }
}
