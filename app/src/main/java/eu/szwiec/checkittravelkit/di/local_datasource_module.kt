package eu.szwiec.checkittravelkit.di

import android.content.Context
import androidx.room.Room
import eu.szwiec.checkittravelkit.repository.local.CountriesJsonReader
import eu.szwiec.checkittravelkit.repository.local.CountryDao
import eu.szwiec.checkittravelkit.repository.local.CountryDb
import org.koin.dsl.module.module

val localDatasourceModule = module {
    single { createDb(get()) }
    single { createDao(get()) }
    single { CountriesJsonReader(get(), get()) }
}

fun createDb(context: Context): CountryDb {
    return Room
            .databaseBuilder(context, CountryDb::class.java, "country.db")
            .build()
}

fun createDao(db: CountryDb): CountryDao {
    return db.countryDao()
}
