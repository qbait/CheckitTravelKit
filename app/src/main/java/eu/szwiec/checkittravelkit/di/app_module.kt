package eu.szwiec.checkittravelkit.di

import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {
    viewModel { SplashViewModel(get()) }
    single { Preferences(get(), "prefs") }
}
