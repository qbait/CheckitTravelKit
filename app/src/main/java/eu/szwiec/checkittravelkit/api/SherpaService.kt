package eu.szwiec.checkittravelkit.api

import androidx.lifecycle.LiveData
import eu.szwiec.checkittravelkit.vo.VisaRequirement
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SherpaService {

    @GET("visa-requirements/{from-to}")
    fun visaRequirements(@Header("Authorization") authHeader: String, @Path("from-to") fromTo: String): LiveData<ApiResponse<List<VisaRequirement>>>

}