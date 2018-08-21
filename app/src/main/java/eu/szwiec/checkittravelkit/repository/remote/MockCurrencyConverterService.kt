package eu.szwiec.checkittravelkit.repository.remote

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eu.szwiec.checkittravelkit.repository.data.Rate
import retrofit2.Response

class MockCurrencyConverterService : CurrencyConverterService {

    override fun convert(from_to: String, compact: String): LiveData<ApiResponse<Map<String, Rate>>> {
        val currencyLD = MutableLiveData<ApiResponse<Map<String, Rate>>>()
        val rate = Rate(2.5f)
        val map = mapOf("XXX_XXX" to rate)
        val apiResponse = ApiResponse(Response.success<Map<String, Rate>>(map))

        Handler().postDelayed({ currencyLD.postValue(apiResponse) }, 500)

        return currencyLD
    }
}