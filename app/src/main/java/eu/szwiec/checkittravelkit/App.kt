package eu.szwiec.checkittravelkit

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import eu.szwiec.checkittravelkit.di.appModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
        AndroidThreeTen.init(this)
    }
}