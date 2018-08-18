package eu.szwiec.checkittravelkit.repository.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eu.szwiec.checkittravelkit.repository.data.Rate
import retrofit2.Response

class MockCurrencyConverterService : CurrencyConverterService {

    override fun convert(from_to: String, compact: String): LiveData<ApiResponse<Map<String, Rate>>> {
        val liveData = MutableLiveData<ApiResponse<Map<String, Rate>>>()
        val rate = Rate(2.5f)
        val map = mapOf("XXX_XXX" to rate)
        var apiResponse = ApiResponse(Response.success<Map<String, Rate>>(map))
        liveData.value = apiResponse

        return liveData
    }
}