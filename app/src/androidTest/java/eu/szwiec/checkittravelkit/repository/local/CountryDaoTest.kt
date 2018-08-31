package eu.szwiec.checkittravelkit.repository.local

import androidx.test.runner.AndroidJUnit4
import eu.szwiec.checkittravelkit.poland
import eu.szwiec.checkittravelkit.thailand
import eu.szwiec.checkittravelkit.util.LiveDataTestUtil.getValue
import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryDaoTest : DbTest() {
    @Test
    fun insertAndLoad() {
        db.countryDao().bulkInsert(listOf(poland, thailand))

        val names = getValue(db.countryDao().getNames())
        names shouldEqual listOf("Poland", "Thailand")
    }
}