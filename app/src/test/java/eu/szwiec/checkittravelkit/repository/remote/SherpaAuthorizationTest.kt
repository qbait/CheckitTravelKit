package eu.szwiec.checkittravelkit.repository.remote

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SherpaAuthorizationTest {

    private val context = mock<Context>()
    val sherpaAuthorization = SherpaAuthorization(context)

    @Test
    fun authIsGeneratedCorrectly() {
        val basic = sherpaAuthorization.generate("username", "password")
        basic shouldEqual "Basic dXNlcm5hbWU6cGFzc3dvcmQ="
    }
}