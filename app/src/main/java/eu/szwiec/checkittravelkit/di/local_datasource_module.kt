package eu.szwiec.checkittravelkit.di

import eu.szwiec.checkittravelkit.repository.local.CountriesJsonReader
import eu.szwiec.checkittravelkit.repository.local.CountryDao
import eu.szwiec.checkittravelkit.repository.local.CountryDb
import org.koin.dsl.module.module

val localDatasourceModule = module {
    single { CountryDb.getInstance(get()) }
    single { createDao(get()) }
    single { CountriesJsonReader(get(), get()) }
}

fun createDao(db: CountryDb): CountryDao {
    return db.countryDao()
}
