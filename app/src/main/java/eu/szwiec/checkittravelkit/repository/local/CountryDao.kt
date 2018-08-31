package eu.szwiec.checkittravelkit.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*
import eu.szwiec.checkittravelkit.repository.data.Country

@Dao
interface CountryDao {
    @Update
    fun update(country: Country)

    @Query("SELECT * FROM country WHERE name = :name")
    fun findByName(name: String): LiveData<Country>

    @Query("SELECT COUNT(id) FROM country")
    fun countCountries(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(countries: List<Country>)

    @Query("SELECT name FROM country")
    fun getNames(): LiveData<List<String>>
}