package dicoding.bangkit.capstone_project.Api

import dicoding.bangkit.capstone_project.Api.Response.KlasifikasiResponse
import dicoding.bangkit.capstone_project.Api.Response.LoginResponse
import dicoding.bangkit.capstone_project.Api.Response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Header("Authorization")token :String,
        @Part file: MultipartBody.Part,
    ): KlasifikasiResponse


    @POST("login")
    fun login(
        @Body body: loginRequest,
    ): Call<LoginResponse>

    @POST("register")
    fun register(
        @Body body: registerRequest,
    ): Call<RegisterResponse>
}

