package eu.szwiec.checkittravelkit.ui.search

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.util.NonNullLiveData
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var vm: SearchViewModel

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockPreferences: Preferences

    @Mock
    lateinit var mockRepository: CountryRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(mockContext.getString(R.string.where_are_you_from)).thenReturn("Where are you from?")
        vm = SearchViewModel(mockContext, mockPreferences, mockRepository)
    }

    @Test
    fun initStateWhenEmptyOrigin() {
        whenever(mockPreferences.origin).thenReturn("")
        vm.initState()

        vm.state.value shouldEqual State.CHOOSE_ORIGIN
    }

    @Test
    fun initStateWhenOriginExistis() {
        whenever(mockPreferences.origin).thenReturn("Poland")
        vm.initState()

        vm.state.value shouldEqual State.CHOOSE_DESTINATION
        vm.origin.value shouldEqual mockPreferences.origin
    }

    @Test
    fun setState() {
        vm.setState(State.CHOOSE_ORIGIN)
        vm.originHint.value shouldEqual mockContext.getString(R.string.where_are_you_from)
        vm.state.value shouldEqual State.CHOOSE_ORIGIN

        vm.setState(State.CHOOSE_DESTINATION)
        vm.originHint.value shouldEqual ""

        vm.setState(State.SHOW_INFO)
        vm.destination.value shouldEqual ""
    }

    @Test
    fun submitOrigin() {
        whenever(mockRepository.getCountryNames()).thenReturn(NonNullLiveData(listOf("Poland", "Germany")))
        vm.state.value = State.CHOOSE_ORIGIN

        vm.origin.value = "Pol"
        vm.submitOrigin()
        vm.state.value shouldEqual State.CHOOSE_ORIGIN

        vm.origin.value = "Poland"
        vm.submitOrigin()
        vm.state.value shouldEqual State.CHOOSE_DESTINATION
    }

    @Test
    fun submitDestination() {
        whenever(mockRepository.getCountryNames()).thenReturn(NonNullLiveData(listOf("Poland", "Germany")))
        vm.state.value = State.CHOOSE_DESTINATION

        vm.destination.value = "Ger"
        vm.submitDestination()
        vm.state.value shouldEqual State.CHOOSE_DESTINATION

        vm.destination.value = "Germany"
        vm.submitDestination()
        vm.state.value shouldEqual State.SHOW_INFO
    }
}