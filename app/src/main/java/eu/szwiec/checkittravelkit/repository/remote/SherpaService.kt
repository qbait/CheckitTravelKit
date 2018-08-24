package eu.szwiec.checkittravelkit.repository.remote

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SherpaService {

    @GET("visa-requirements/{from-to}")
    fun visaRequirements(@Header("Authorization") authHeader: String, @Path("from-to") fromTo: String): LiveData<ApiResponse<String>>

}