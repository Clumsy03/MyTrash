package dicoding.bangkit.capstone_project.ui.Hasil_klasifikasi

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import dicoding.bangkit.capstone_project.databinding.HasilKlasifikasiBinding
import androidx.activity.viewModels
import dicoding.bangkit.capstone_project.R

class Hasil_klasifikasi : AppCompatActivity() {

    private val viewModel: HasilKlasifikasiViewModel by viewModels()

    private lateinit var binding : HasilKlasifikasiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HasilKlasifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getStringExtra("EXTRA_RESULT")
        val suggestion = intent.getStringExtra("EXTRA_SUGGESTION")
        val imageUriString = intent.getStringExtra("EXTRA_IMAGE_URI")

        findViewById<TextView>(R.id.hasil_tipe).text = result
        findViewById<TextView>(R.id.hasil_sugestion).text = suggestion
        imageUriString?.let { uriString ->
            val imageUri = Uri.parse(uriString)
            findViewById<ImageView>(R.id.image_klasifikasi).setImageURI(imageUri)
        }


        setupView()
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
}