package eu.szwiec.checkittravelkit

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import eu.szwiec.checkittravelkit.di.appModule
import eu.szwiec.checkittravelkit.di.localDatasourceModule
import eu.szwiec.checkittravelkit.di.mockRemoteDataSourceModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, mockRemoteDataSourceModule, localDatasourceModule))
        AndroidThreeTen.init(this)
    }
}