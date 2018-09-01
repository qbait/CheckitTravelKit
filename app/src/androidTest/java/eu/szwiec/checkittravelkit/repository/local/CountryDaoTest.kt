package eu.szwiec.checkittravelkit.repository.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import eu.szwiec.checkittravelkit.poland
import eu.szwiec.checkittravelkit.thailand
import eu.szwiec.checkittravelkit.util.LiveDataTestUtil.getValue
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryDaoTest : DbTest() {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun update() {
        db.countryDao().bulkInsert(listOf(poland))

        val updatedPoland = poland.copy(tapWater = "updated info")
        db.countryDao().update(updatedPoland)

        val polandFromDb = getValue(db.countryDao().findByName(poland.name))

        polandFromDb shouldEqual updatedPoland
    }

    @Test
    fun findByName() {
        db.countryDao().bulkInsert(listOf(poland))

        val polandFromDb = getValue(db.countryDao().findByName(poland.name))
        polandFromDb shouldEqual poland
    }

    @Test
    fun countCountries() {
        db.countryDao().bulkInsert(listOf(poland, thailand))

        val counter = db.countryDao().countCountries()
        counter shouldEqual 2
    }

    @Test
    fun bulkInsert() {
        db.countryDao().bulkInsert(listOf(poland, thailand))

        val polandFromDb = getValue(db.countryDao().findByName(poland.name))
        val thailandFromDb = getValue(db.countryDao().findByName(thailand.name))

        polandFromDb shouldEqual poland
        thailandFromDb shouldEqual thailand
    }

    @Test
    fun getNames() {
        db.countryDao().bulkInsert(listOf(poland, thailand))

        val names = getValue(db.countryDao().getNames())
        names shouldEqual listOf(poland.name, thailand.name)
    }
}