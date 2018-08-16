package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import android.graphics.drawable.Drawable
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import eu.szwiec.checkittravelkit.R
import org.amshove.kluent.shouldEqual
import org.junit.Test

class PlugProviderTest {

    val context = mock<Context>()
    val plugProvider = PlugProvider(context)

    @Test
    fun provideWhenPlugNotExists() {
        val wrongSymbol = "wrong symbol"
        val mockDefaultDrawable = mock<Drawable>()
        whenever(context.getDrawable(R.drawable.ic_plug)).thenReturn(mockDefaultDrawable)
        val plug = plugProvider.provide(wrongSymbol)

        plug.symbol shouldEqual wrongSymbol
        plug.icon shouldEqual mockDefaultDrawable
    }

    @Test
    fun provideWhenPlugExists() {
        val A = "A"
        val mockADrawable = mock<Drawable>()

        whenever(context.getDrawable(R.drawable.plug_a)).thenReturn(mockADrawable)
        val plug = plugProvider.provide(A)

        plug.symbol shouldEqual A
        plug.icon shouldEqual mockADrawable
    }
}