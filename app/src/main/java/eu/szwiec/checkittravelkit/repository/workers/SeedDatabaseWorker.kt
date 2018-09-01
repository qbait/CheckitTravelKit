package com.google.samples.apps.sunflower.workers

import android.util.Log
import androidx.work.Worker
import eu.szwiec.checkittravelkit.repository.local.CountriesJsonReader
import eu.szwiec.checkittravelkit.repository.local.CountryDao
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SeedDatabaseWorker : Worker(), KoinComponent {
    private val TAG = SeedDatabaseWorker::class.java.simpleName

    override fun doWork(): Worker.Result {
        val jsonReader: CountriesJsonReader by inject()
        val dao: CountryDao by inject()

        return try {
            if (dao.countCountries() == 0) {
                val countries = jsonReader.getCountries()
                dao.bulkInsert(countries)
            }
            Worker.Result.SUCCESS
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Worker.Result.FAILURE
        }

    }
}