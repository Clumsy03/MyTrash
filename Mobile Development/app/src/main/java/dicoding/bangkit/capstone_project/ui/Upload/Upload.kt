package dicoding.bangkit.capstone_project.ui.Upload
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import dicoding.bangkit.capstone_project.Api.ApiConfig
import dicoding.bangkit.capstone_project.Helper.getImageUri
import dicoding.bangkit.capstone_project.Helper.reduceFileImage
import dicoding.bangkit.capstone_project.Helper.uriToFile
import dicoding.bangkit.capstone_project.ui.Hasil_klasifikasi.Hasil_klasifikasi
import dicoding.bangkit.capstone_project.ui.homepage.Homepage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.load.HttpException
import com.dicoding.picodiploma.loginwithanimation.data.sharedpreference.sharedpreferencetoken
import dicoding.bangkit.capstone_project.R
import dicoding.bangkit.capstone_project.databinding.KlasifikasiActifityBinding
import dicoding.bangkit.capstone_project.ui.Hasil_klasifikasi.HasilKlasifikasiViewModel
import kotlinx.coroutines.launch

class Upload : AppCompatActivity() {

    private lateinit var binding : KlasifikasiActifityBinding
    private var currentImageUri : Uri? = null
    private lateinit var viewmodel : HasilKlasifikasiViewModel
    private lateinit var sharedpreferencetoken: sharedpreferencetoken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = KlasifikasiActifityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        sharedpreferencetoken = sharedpreferencetoken(this)

        binding.buttonGalery.setOnClickListener{Startgalery() }
        binding.buttonCamera.setOnClickListener{Startcamera()}
        binding.backbutton.setOnClickListener {
            startActivity(Intent(this@Upload , Homepage::class.java))
        }
        binding.buttonAnalyze.setOnClickListener{
           classifyImage()
        }
        val t = sharedpreferencetoken.getToken()
        val token = " Bearer $t"
    }

    private fun classifyImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            showLoading(true)

            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )

            val t = sharedpreferencetoken.getToken().toString()
            val token = " Bearer $t"

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                     println(apiService)
                    val klasifikasiResponse = apiService.uploadImage(token ,multipartBody)
                    val result = klasifikasiResponse.data?.result
                    val suggestion = klasifikasiResponse.data?.suggestion

                    val intent = Intent(this@Upload, Hasil_klasifikasi::class.java).apply {
                        putExtra("EXTRA_RESULT", result)
                        putExtra("EXTRA_SUGGESTION", suggestion)
                        putExtra("EXTRA_IMAGE_URI", currentImageUri.toString())
                    }
                    startActivity(intent)
                    showLoading(false)
                } catch (e: HttpException) {
                    showToast(e.message.toString())
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }


    private fun Startgalery(){
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun Startcamera(){
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){ isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }



    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.buttonGalery.setImageURI(it)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}