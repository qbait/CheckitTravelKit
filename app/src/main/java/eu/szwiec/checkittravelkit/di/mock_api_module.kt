package eu.szwiec.checkittravelkit.di

import eu.szwiec.checkittravelkit.api.MockCurrencyConverterService
import eu.szwiec.checkittravelkit.api.MockSherpaService
import org.koin.dsl.module.module

val mockApiModule = module {
    single { MockCurrencyConverterService() }
    single { MockSherpaService(get(), get(), "visa.json") }
}