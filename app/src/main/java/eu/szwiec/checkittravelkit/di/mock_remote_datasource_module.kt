package eu.szwiec.checkittravelkit.di

import eu.szwiec.checkittravelkit.repository.remote.CurrencyConverterService
import eu.szwiec.checkittravelkit.repository.remote.MockCurrencyConverterService
import eu.szwiec.checkittravelkit.repository.remote.MockSherpaService
import eu.szwiec.checkittravelkit.repository.remote.SherpaService
import org.koin.dsl.module.module

val mockRemoteDatasourceModule = module {
    single { MockCurrencyConverterService() as CurrencyConverterService }
    single { MockSherpaService() as SherpaService }
}