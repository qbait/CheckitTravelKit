package eu.szwiec.checkittravelkit.ui.search

import android.content.Context
import eu.szwiec.checkittravelkit.prefs.Preferences
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FavoritesViewModelTest {

    lateinit var favoritesViewModel: FavoritesViewModel

    val header = "header"
    val footer = "footer";

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockPreferences: Preferences

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        favoritesViewModel = FavoritesViewModel(mockContext, mockPreferences)
    }

    @Test
    fun noItemsWhenNoFavorites() {
        val items = favoritesViewModel.getNewItems(emptySet(), header, footer)
        assertEquals(0, items.count())
    }

    @Test
    fun itemsAreCorrect() {
        val poland = "Poland"
        val germany = "Germany"

        val items = favoritesViewModel.getNewItems(setOf(poland, germany), header, footer)
        assertEquals(4, items.count())
        assertEquals(header, items[0])
        assertEquals(germany, (items[1] as FavoriteCountry).name)
        assertEquals(poland, (items[2] as FavoriteCountry).name)
        assertEquals(footer, items[3])

    }
}