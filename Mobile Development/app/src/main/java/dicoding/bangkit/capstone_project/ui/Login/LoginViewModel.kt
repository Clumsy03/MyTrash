package dicoding.bangkit.capstone_project.ui.Login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dicoding.bangkit.capstone_project.Api.ApiConfig
import dicoding.bangkit.capstone_project.Api.Response.LoginResponse
import dicoding.bangkit.capstone_project.Api.loginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel(){

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun postlogin(authBody : loginRequest){
        ApiConfig.getApiService().login(authBody).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.d("TAG", "Login successful: ${response.body().toString()}")
                    _loginResponse.postValue(response.body())
                } else{
                    Log.d("TAG", "Login Response Failed")
                    _loginResponse.postValue(LoginResponse("null", null))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("TAG", t.message.toString())
                _loginResponse.postValue(LoginResponse("null", null))
            }
        })
    }
}