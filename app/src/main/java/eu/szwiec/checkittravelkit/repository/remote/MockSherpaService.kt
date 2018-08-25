package eu.szwiec.checkittravelkit.repository.remote

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eu.szwiec.checkittravelkit.repository.data.Visa

class MockSherpaService : SherpaService {

    override fun visaRequirements(auth: String, fromTo: String): LiveData<ApiResponse<Visa>> {

        val ld = MutableLiveData<ApiResponse<Visa>>()
        val apiResponse = ApiSuccessResponse(Visa("Visa is required", "PL", System.currentTimeMillis()))

        Handler().postDelayed({ ld.postValue(apiResponse) }, 1000)

        return ld
    }
}