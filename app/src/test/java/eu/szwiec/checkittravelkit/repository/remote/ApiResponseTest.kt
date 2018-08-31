package eu.szwiec.checkittravelkit.repository.remote

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.amshove.kluent.shouldEqual
import org.junit.Test
import retrofit2.Response

class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("foo")
        val (errorMessage) = ApiResponse.create<String>(exception)
        errorMessage shouldEqual "foo"
    }

    @Test
    fun success() {
        val apiResponse: ApiSuccessResponse<String> = ApiResponse.create<String>(Response.success("foo")) as ApiSuccessResponse<String>
        apiResponse.body shouldEqual "foo"
    }

    @Test
    fun error() {
        val errorResponse = Response.error<String>(400, ResponseBody.create(MediaType.parse("application/txt"), "blah"))
        val (errorMessage) = ApiResponse.create<String>(errorResponse) as ApiErrorResponse<String>
        errorMessage shouldEqual "blah"
    }
}