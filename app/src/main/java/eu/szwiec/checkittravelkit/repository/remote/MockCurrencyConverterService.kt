package eu.szwiec.checkittravelkit.repository.remote

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eu.szwiec.checkittravelkit.repository.data.Rate

class MockCurrencyConverterService : CurrencyConverterService {

    override fun convert(from_to: String, compact: String): LiveData<ApiResponse<Rate>> {
        val currencyLD = MutableLiveData<ApiResponse<Rate>>()
        val apiResponse = ApiSuccessResponse(Rate(0.5, "PLN", System.currentTimeMillis()))
        Handler().postDelayed({ currencyLD.postValue(apiResponse) }, 500)

        return currencyLD
    }
}