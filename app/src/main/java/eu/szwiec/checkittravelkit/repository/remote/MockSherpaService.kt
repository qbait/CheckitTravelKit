package eu.szwiec.checkittravelkit.repository.remote

import android.content.Context
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.Moshi
import eu.szwiec.checkittravelkit.repository.data.Visa

class MockSherpaService(val context: Context, val moshi: Moshi, val file: String) : SherpaService {

    override fun visaRequirements(auth: String, fromTo: String): LiveData<ApiResponse<Visa>> {

        val ld = MutableLiveData<ApiResponse<Visa>>()
        val apiResponse = ApiSuccessResponse(Visa("Visa is required", "PL", System.currentTimeMillis()))

        Handler().postDelayed({ ld.postValue(apiResponse) }, 1000)

        return ld
    }
}