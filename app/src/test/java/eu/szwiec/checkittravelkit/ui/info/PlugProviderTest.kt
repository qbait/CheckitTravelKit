package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.resId
import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PlugProviderTest : AutoCloseKoinTest() {

    val context: Context by inject()
    val plugProvider = PlugProvider(context)

    @Test
    fun provideWhenPlugNotExists() {
        val wrongSymbol = "wrong symbol"
        val plug = plugProvider.provide(wrongSymbol)

        plug.symbol shouldEqual wrongSymbol
        plug.icon.resId shouldEqual R.drawable.ic_plug
    }

    @Test
    fun provideWhenPlugExists() {
        val A = "A"
        val plug = plugProvider.provide(A)

        plug.symbol shouldEqual A
        plug.icon.resId shouldEqual R.drawable.plug_a
    }
}