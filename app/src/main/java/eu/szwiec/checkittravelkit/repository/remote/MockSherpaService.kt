package eu.szwiec.checkittravelkit.repository.remote

import android.content.Context
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.Moshi

class MockSherpaService(val context: Context, val moshi: Moshi, val file: String) : SherpaService {

    override fun visaRequirements(auth: String, fromTo: String): LiveData<ApiResponse<String>> {

        val ld = MutableLiveData<ApiResponse<String>>()
        val apiResponse = ApiSuccessResponse("Visa is required")

        Handler().postDelayed({ ld.postValue(apiResponse) }, 1000)

        return ld
    }
}