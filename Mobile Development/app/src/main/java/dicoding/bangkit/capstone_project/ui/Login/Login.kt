package dicoding.bangkit.capstone_project.ui.Login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.loginwithanimation.data.sharedpreference.sharedpreferencetoken
import dicoding.bangkit.capstone_project.databinding.ActivityLoginBinding
import dicoding.bangkit.capstone_project.ui.homepage.Homepage
import dicoding.bangkit.capstone_project.Api.loginRequest
import dicoding.bangkit.capstone_project.ui.register.Register

class Login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedpreferencetoken: sharedpreferencetoken
    private var token : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedpreferencetoken = sharedpreferencetoken(this)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        token = sharedpreferencetoken.getToken()
        setupView()
        playAnimation()
        validatelogin()


        binding.signUp.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

    }

    private fun validatelogin(){
        binding.btnSignIn.setOnClickListener{
            val email = binding.inputEmail
            val password = binding.inputPassword
            if (email.text.toString().isEmpty() || password.text.toString().isEmpty()){
                Toast.makeText(this, "Pastikan anda telah mengisi input email dan password!", Toast.LENGTH_SHORT).show()
            }
            if (email.isEmailValid && password.isPasswordValid){
                viewModel.postlogin(loginRequest(email.text.toString(), password.text.toString()))
                viewModel.loginResponse.observe(this){ response ->
                    showLoading(false)
                    response?.let {
                        if (it.userCredential != null) {
                            val user = it.userCredential.user
                            val token = it.userCredential.tokenResponse?.idToken
                            val providerData = user?.providerData?.firstOrNull()
                            val name = providerData?.displayName ?: ""

                            sharedpreferencetoken.saveToken(token ?: "", name, email.text.toString())
                            startActivity(Intent(this, Homepage::class.java))
                            finish()
                        } else {
                            Log.d("TAG", "userCredential is null")
                            Toast.makeText(this, "Login Failed. Try Again!", Toast.LENGTH_SHORT).show()
                        }
                    } ?: run {
                        Log.d("TAG", "Response is null")
                        Toast.makeText(this, "Login Failed. Try Again!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Invalid email or password format!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    private fun playAnimation() {
        val duration = 380L

        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(duration)
        val titleDesc =
            ObjectAnimator.ofFloat(binding.tvLoginDes, View.ALPHA, 1f).setDuration(duration)
        val email = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(duration)
        val inputEmail =
            ObjectAnimator.ofFloat(binding.inputEmailLayout, View.ALPHA, 1f).setDuration(duration)
        val password =
            ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(duration)
        val inputPassword = ObjectAnimator.ofFloat(binding.inputPasswordLayout, View.ALPHA, 1f)
            .setDuration(duration)
        val btnSignIn =
            ObjectAnimator.ofFloat(binding.btnSignIn, View.ALPHA, 1f).setDuration(duration)
        val tvOr = ObjectAnimator.ofFloat(binding.tvOr, View.ALPHA, 1f).setDuration(duration)
        val signUp = ObjectAnimator.ofFloat(binding.signUp, View.ALPHA, 1f).setDuration(duration)

        AnimatorSet().apply {
            playSequentially(
                title,
                titleDesc,
                email,
                inputEmail,
                password,
                inputPassword,
                btnSignIn,
                tvOr,
                signUp
            )
            start()
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.isVisible = state
    }

}