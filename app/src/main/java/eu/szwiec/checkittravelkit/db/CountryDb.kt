package eu.szwiec.checkittravelkit.db

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.szwiec.checkittravelkit.vo.Country

@Database(entities = arrayOf(Country::class), version = 1)
abstract class CountryDb : RoomDatabase() {

    abstract fun countryDao(): CountryDao
}