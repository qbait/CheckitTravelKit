package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import android.graphics.drawable.Drawable
import eu.szwiec.checkittravelkit.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class PlugProviderTest {

    lateinit var plugProvider: PlugProvider

    @Mock
    lateinit var mockContext: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        plugProvider = PlugProvider(mockContext)
    }

    @Test
    fun provideWhenPlugNotExists() {
        val wrongSymbol = "wrong symbol"
        val mockDefaultDrawable = mock(Drawable::class.java)
        `when`(mockContext.getDrawable(R.drawable.ic_plug)).thenReturn(mockDefaultDrawable)
        val plug = plugProvider.provide(wrongSymbol)

        assertEquals(wrongSymbol, plug.symbol)
        assertEquals(mockDefaultDrawable, plug.icon)
    }

    @Test
    fun provideWhenPlugExists() {
        val A = "A"
        val mockADrawable = mock(Drawable::class.java)

        `when`(mockContext.getDrawable(R.drawable.plug_a)).thenReturn(mockADrawable)
        val plug = plugProvider.provide(A)

        assertEquals(A, plug.symbol)
        assertEquals(mockADrawable, plug.icon)
    }
}