package eu.szwiec.checkittravelkit.repository.remote

import androidx.lifecycle.LiveData
import eu.szwiec.checkittravelkit.repository.data.Rate
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConverterService {

    @GET("convert")
    fun convert(@Query("q") from_to: String, @Query("compact") compact: String): LiveData<ApiResponse<Map<String, Rate>>>
}