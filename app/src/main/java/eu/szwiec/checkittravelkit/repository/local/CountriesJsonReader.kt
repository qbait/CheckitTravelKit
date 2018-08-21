package eu.szwiec.checkittravelkit.repository.local

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import eu.szwiec.checkittravelkit.repository.data.Country
import java.io.BufferedReader
import java.io.InputStreamReader

class CountriesJsonReader(private val context: Context, private val moshi: Moshi, private val filename: String = "countries.json") {

    fun getCountries(): List<Country> {
        val json = readFile()
        return parse(json)
    }

    private fun readFile(): String {
        val buf = StringBuilder()
        val json = context.assets.open(filename)
        BufferedReader(InputStreamReader(json, "UTF-8"))
                .use {
                    val list = it.lineSequence().toList()
                    buf.append(list.joinToString("\n"))
                }

        return buf.toString()
    }

    private fun parse(json: String): List<Country> {
        val listOfCountriesType = Types.newParameterizedType(List::class.java, Country::class.java)
        val listOfCountriesAdapter = moshi.adapter<List<Country>>(listOfCountriesType)
        val countries = listOfCountriesAdapter.fromJson(json)

        return countries.orEmpty()
    }
}