package eu.szwiec.checkittravelkit.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.repository.CountryRepositoryImpl
import eu.szwiec.checkittravelkit.repository.remote.SherpaAuthorization
import eu.szwiec.checkittravelkit.ui.info.InfoViewModel
import eu.szwiec.checkittravelkit.ui.info.PlugProvider
import eu.szwiec.checkittravelkit.ui.search.FavoritesViewModel
import eu.szwiec.checkittravelkit.ui.search.SearchViewModel
import eu.szwiec.checkittravelkit.ui.splash.SplashViewModel
import eu.szwiec.checkittravelkit.util.AppExecutors
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {

    viewModel { SplashViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get()) }
    viewModel { InfoViewModel(get(), get(), get(), get()) }

    single { Preferences(get(), "prefs") }
    single { CountryRepositoryImpl(get(), get(), get(), get(), get(), get()) as CountryRepository }
    single { SherpaAuthorization(get()) }
    single { PlugProvider(get()) }
    single { AppExecutors() }
    single { createMoshi() }

}

fun createMoshi(): Moshi {
    return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
}