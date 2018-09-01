package eu.szwiec.checkittravelkit.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.samples.apps.sunflower.workers.SeedDatabaseWorker
import eu.szwiec.checkittravelkit.repository.local.CountriesJsonReader
import eu.szwiec.checkittravelkit.repository.local.CountryDao
import eu.szwiec.checkittravelkit.repository.local.CountryDb
import org.koin.dsl.module.module

val localDatasourceModule = module {
    single { createDb(get()) }
    single { createDao(get()) }
    single { CountriesJsonReader(get(), get()) }
}

fun createDb(context: Context): CountryDb {
    return Room
            .databaseBuilder(context, CountryDb::class.java, "country.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                    WorkManager.getInstance().enqueue(request)
                }
            })
            .build()
}

fun createDao(db: CountryDb): CountryDao {
    return db.countryDao()
}
