package eu.szwiec.checkittravelkit.di

import eu.szwiec.checkittravelkit.repository.remote.MockCurrencyConverterService
import eu.szwiec.checkittravelkit.repository.remote.MockSherpaService
import org.koin.dsl.module.module

val mockRemoteDataSourceModule = module {
    single { MockCurrencyConverterService() }
    single { MockSherpaService(get(), get(), "visa.json") }
}