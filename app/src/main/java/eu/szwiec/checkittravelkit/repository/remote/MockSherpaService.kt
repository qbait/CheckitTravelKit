package eu.szwiec.checkittravelkit.repository.remote

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import eu.szwiec.checkittravelkit.repository.data.VisaRequirement
import eu.szwiec.checkittravelkit.util.readFile
import retrofit2.Response

class MockSherpaService(val context: Context, val moshi: Moshi, val file: String) : SherpaService {

    override fun visaRequirements(auth: String, fromTo: String): LiveData<ApiResponse<List<VisaRequirement>>> {

        val liveData = MutableLiveData<ApiResponse<List<VisaRequirement>>>()
        val visaJson = readFile(context, file)
        val visaRequirements = parseVisaRequirements(visaJson)

        var apiResponse = ApiResponse(Response.success<List<VisaRequirement>>(visaRequirements))
        liveData.value = apiResponse

        return liveData
    }

    fun parseVisaRequirements(json: String): List<VisaRequirement> {
        val listOfVisaRequirementsType = Types.newParameterizedType(List::class.java, VisaRequirement::class.java)
        val listOfVisaRequirementsAdapter = moshi.adapter<List<VisaRequirement>>(listOfVisaRequirementsType)
        return listOfVisaRequirementsAdapter.fromJson(json).orEmpty()
    }
}