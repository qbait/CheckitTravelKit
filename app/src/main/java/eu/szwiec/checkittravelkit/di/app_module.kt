package eu.szwiec.checkittravelkit.di

import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.ui.info.InfoViewModel
import eu.szwiec.checkittravelkit.ui.info.PlugProvider
import eu.szwiec.checkittravelkit.ui.search.FavoritesViewModel
import eu.szwiec.checkittravelkit.ui.search.SearchViewModel
import eu.szwiec.checkittravelkit.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {

    viewModel { SplashViewModel(get()) }
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get()) }
    viewModel { InfoViewModel(get(), get(), get(), get()) }

    single { Preferences(get(), "prefs") }
    single { CountryRepository() }
    single { PlugProvider(get()) }
}
