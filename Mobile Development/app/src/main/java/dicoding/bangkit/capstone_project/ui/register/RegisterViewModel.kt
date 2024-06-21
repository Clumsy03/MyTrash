package dicoding.bangkit.capstone_project.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dicoding.bangkit.capstone_project.Api.ApiConfig
import dicoding.bangkit.capstone_project.Api.Response.RegisterResponse
import dicoding.bangkit.capstone_project.Api.registerRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    fun postRegister(authBody: registerRequest) {
        ApiConfig.getApiService().register(authBody).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    Log.d("TAG", "register successful: ${response.body().toString()}")
                    _registerResponse.postValue(response.body())
                } else {
                    _registerResponse.postValue(RegisterResponse("Register failed"))
                    Log.d("TAG", "Register Response Failed")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _registerResponse.postValue(RegisterResponse("Register failed"))
                Log.d("TAG", t.message.toString())
            }
        })
    }
}