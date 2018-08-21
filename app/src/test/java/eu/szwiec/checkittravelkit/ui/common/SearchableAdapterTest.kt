package eu.szwiec.checkittravelkit.ui.common

import android.content.Context
import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchableAdapterTest : AutoCloseKoinTest() {

    private val context: Context by inject()
    private val adapter = SearchableAdapter(context, emptyList())

    @Test
    fun isMatching() {
        adapter.isMatching("United Kingdom", "uk") shouldEqual true
        adapter.isMatching("Poland", "ol") shouldEqual false
        adapter.isMatching("Poland", "pol") shouldEqual true
        adapter.isMatching("Poland", "pland") shouldEqual true
    }
}