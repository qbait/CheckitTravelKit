package eu.szwiec.checkittravelkit

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import eu.szwiec.checkittravelkit.di.appModule
import eu.szwiec.checkittravelkit.di.localDatasourceModule
import eu.szwiec.checkittravelkit.di.mockRemoteDataSourceModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin(this, listOf(appModule, mockRemoteDataSourceModule, localDatasourceModule))
        AndroidThreeTen.init(this)
    }
}