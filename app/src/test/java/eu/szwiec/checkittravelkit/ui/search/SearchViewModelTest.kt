package eu.szwiec.checkittravelkit.ui.search

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.util.NonNullLiveData
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var searchViewModel: SearchViewModel

    @Mock
    lateinit var mockResources: Resources

    @Mock
    lateinit var mockPreferences: Preferences

    @Mock
    lateinit var mockRepository: CountryRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(mockResources.getString(R.string.where_are_you_from)).thenReturn("Where are you from?")
        searchViewModel = SearchViewModel(mockResources, mockPreferences, mockRepository)
    }

    @Test
    fun initStateWhenEmptyOrigin() {
        Mockito.`when`(mockPreferences.origin).thenReturn("")
        searchViewModel.initState()

        assertEquals(State.CHOOSE_ORIGIN, searchViewModel.state.value)
    }

    @Test
    fun initStateWhenOriginExistis() {
        Mockito.`when`(mockPreferences.origin).thenReturn("Poland")
        searchViewModel.initState()

        assertEquals(State.CHOOSE_DESTINATION, searchViewModel.state.value)
        assertEquals(mockPreferences.origin, searchViewModel.origin.value)
    }

    @Test
    fun setState() {
        searchViewModel.setState(State.CHOOSE_ORIGIN)
        assertEquals(mockResources.getString(R.string.where_are_you_from), searchViewModel.originHint.value)
        assertEquals(State.CHOOSE_ORIGIN, searchViewModel.state.value)

        searchViewModel.setState(State.CHOOSE_DESTINATION)
        assertEquals("", searchViewModel.originHint.value)

        searchViewModel.setState(State.SHOW_INFO)
        assertEquals("", searchViewModel.destination.value)
    }

    @Test
    fun submitOrigin() {
        Mockito.`when`(mockRepository.getCountryNames()).thenReturn(NonNullLiveData(listOf("Poland", "Germany")))
        searchViewModel.state.value = State.CHOOSE_ORIGIN

        searchViewModel.origin.value = "Pol"
        searchViewModel.submitOrigin()
        assertEquals(State.CHOOSE_ORIGIN, searchViewModel.state.value)

        searchViewModel.origin.value = "Poland"
        searchViewModel.submitOrigin()
        assertEquals(State.CHOOSE_DESTINATION, searchViewModel.state.value)
    }

    @Test
    fun submitDestination() {
        Mockito.`when`(mockRepository.getCountryNames()).thenReturn(NonNullLiveData(listOf("Poland", "Germany")))
        searchViewModel.state.value = State.CHOOSE_DESTINATION

        searchViewModel.destination.value = "Ger"
        searchViewModel.submitDestination()
        assertEquals(State.CHOOSE_DESTINATION, searchViewModel.state.value)

        searchViewModel.destination.value = "Germany"
        searchViewModel.submitDestination()
        assertEquals(State.SHOW_INFO, searchViewModel.state.value)
    }
}