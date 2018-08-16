package eu.szwiec.checkittravelkit.ui.search

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import eu.szwiec.checkittravelkit.prefs.Preferences
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldEqual
import org.junit.Test

class FavoritesViewModelTest {

    val header = "header"
    val footer = "footer";

    val context = mock<Context>()
    val preferences = mock<Preferences>()
    val vm = FavoritesViewModel(context, preferences)

    @Test
    fun noItemsWhenNoFavorites() {
        val items = vm.getNewItems(emptySet(), header, footer)
        items.shouldBeEmpty()
    }

    @Test
    fun itemsAreCorrect() {
        val poland = "Poland"
        val germany = "Germany"

        val items = vm.getNewItems(setOf(poland, germany), header, footer)

        items.count() shouldEqual 4
        items[0] shouldEqual header
        (items[1] as FavoriteCountry).name shouldEqual germany
        (items[2] as FavoriteCountry).name shouldEqual poland
        items[3] shouldEqual footer
    }
}