package eu.szwiec.checkittravelkit.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.szwiec.checkittravelkit.repository.data.Country

@Database(entities = [Country::class], version = 1)
abstract class CountryDb : RoomDatabase() {

    abstract fun countryDao(): CountryDao
}