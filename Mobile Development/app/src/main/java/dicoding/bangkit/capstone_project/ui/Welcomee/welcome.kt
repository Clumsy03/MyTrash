package dicoding.bangkit.capstone_project.ui.Welcomee

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.dicoding.picodiploma.loginwithanimation.data.sharedpreference.sharedpreferencetoken
import dicoding.bangkit.capstone_project.databinding.ActivityWelcomeBinding
import dicoding.bangkit.capstone_project.ui.Login.Login
import dicoding.bangkit.capstone_project.ui.homepage.Homepage
import dicoding.bangkit.capstone_project.ui.register.Register

class welcome : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var sharedpreferencetoken: sharedpreferencetoken
    private var token : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedpreferencetoken = sharedpreferencetoken(this)
        token = sharedpreferencetoken.getToken()
        cekToken()
    }
    private fun cekToken() {
        if (!token.isNullOrEmpty()){
            startActivity(Intent(this@welcome, Homepage::class.java))
            finish()
        }
        else{
            setupView()
            setupAction()
            playAnimation()

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

    private fun setupAction() {
        binding.loginbuton.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.loginbuton, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val desc = ObjectAnimator.ofFloat(binding.descTextView, View.ALPHA, 1f).setDuration(100)

        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }

        AnimatorSet().apply {
            playSequentially(title, desc, together)
            start()
        }
    }
}