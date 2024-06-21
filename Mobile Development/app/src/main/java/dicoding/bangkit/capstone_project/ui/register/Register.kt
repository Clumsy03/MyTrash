package dicoding.bangkit.capstone_project.ui.register

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
import dicoding.bangkit.capstone_project.Api.registerRequest
import dicoding.bangkit.capstone_project.databinding.RegisterActifityBinding
import dicoding.bangkit.capstone_project.ui.Login.Login

class Register : AppCompatActivity() {

    private lateinit var binding: RegisterActifityBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = RegisterActifityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        setupView()

        validateregister()


        binding.signin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

    private fun validateregister() {
        binding.btnSignUp.setOnClickListener {
            val email = binding.inputEmail
            val password = binding.inputPassword
            val username = binding.inputname

            if (username.text.toString().isEmpty() || email.text.toString().isEmpty() || password.text.toString().isEmpty() || username.text.toString().isEmpty()){
                Toast.makeText(this, "pastikan anda sudah mengisi Email, Password, Dan Username!", Toast.LENGTH_SHORT).show()
            }
            if (username.isUsernameValid && email.isEmailValid && password.isPasswordValid){
                showLoading(true)
                showLoading(true)
                viewModel.postRegister(registerRequest(username.text.toString(), email.text.toString(), password.text.toString()))
                viewModel.registerResponse.observe(this) { response ->
                    Log.d("TAG", "Hasil Register : $response")
                    showLoading(false)
                    if (response != null) {
                        startActivity(Intent(this@Register, Login::class.java))
                        finish()
                    } else {
                        val errorMessage = response?.message ?: "Register Failed. Try again!"
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Validation Failed: Check your input fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun playAnimation() {
        val duration = 380L

        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(duration)
        val titleDesc =
            ObjectAnimator.ofFloat(binding.tvRegisterDes, View.ALPHA, 1f).setDuration(duration)
        val username = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(duration)
        val inputUsername =
            ObjectAnimator.ofFloat(binding.inputNameLayout, View.ALPHA, 1f).setDuration(duration)
        val email = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(duration)
        val inputEmail =
            ObjectAnimator.ofFloat(binding.inputEmailLayout, View.ALPHA, 1f).setDuration(duration)
        val password =
            ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(duration)
        val inputPassword = ObjectAnimator.ofFloat(binding.inputPasswordLayout, View.ALPHA, 1f)
            .setDuration(duration)
        val btnsignup =
            ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(duration)
        val tvOr = ObjectAnimator.ofFloat(binding.tvOr, View.ALPHA, 1f).setDuration(duration)
        val signin = ObjectAnimator.ofFloat(binding.signin, View.ALPHA, 1f).setDuration(duration)

        AnimatorSet().apply {
            playSequentially(
                title,
                titleDesc,
                username,
                inputUsername,
                email,
                inputEmail,
                password,
                inputPassword,
                btnsignup,
                tvOr,
                signin
            )
            start()
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
    private fun showLoading(state: Boolean) {
        binding.progressBar.isVisible = state
    }
}