package eu.szwiec.checkittravelkit.repository.local

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Converters() {

    val moshi = Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    val listOfStringsType = Types.newParameterizedType(List::class.java, String::class.java)
    val listOfStringsAdapter = moshi.adapter<List<String>>(listOfStringsType)
    val mapOfStringsType = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
    val mapOfStringsAdapter = moshi.adapter<Map<String, String>>(mapOfStringsType)

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
