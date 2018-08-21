package eu.szwiec.checkittravelkit.repository.local

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {

    val moshi: Moshi = Moshi.Builder().build()

    private val listOfStringsType = Types.newParameterizedType(List::class.java, String::class.java)
    private val listOfStringsAdapter = moshi.adapter<List<String>>(listOfStringsType)
    private val mapOfStringsType = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
    private val mapOfStringsAdapter = moshi.adapter<Map<String, String>>(mapOfStringsType)

    @TypeConverter
    fun stringToList(data: String): List<String> {
        return listOfStringsAdapter.fromJson(data).orEmpty()
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        return listOfStringsAdapter.toJson(list)
    }

    @TypeConverter
    fun stringToMap(data: String): Map<String, String> {
        return mapOfStringsAdapter.fromJson(data).orEmpty()
    }

    @TypeConverter
    fun mapToString(map: Map<String, String>): String {
        return mapOfStringsAdapter.toJson(map)
    }
}
