package dicoding.bangkit.capstone_project.ui.homepage

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.loginwithanimation.data.sharedpreference.sharedpreferencetoken
import dicoding.bangkit.capstone_project.Data.Data_artikel
import dicoding.bangkit.capstone_project.R
import dicoding.bangkit.capstone_project.adapter.ArtikelAdapter
import dicoding.bangkit.capstone_project.databinding.HomeActifityBinding
import dicoding.bangkit.capstone_project.ui.Detail_artikel.Detail_article
import dicoding.bangkit.capstone_project.ui.Upload.Upload
import dicoding.bangkit.capstone_project.ui.Welcomee.welcome

class Homepage : AppCompatActivity() {

    private lateinit var binding : HomeActifityBinding
    private lateinit var artikel : RecyclerView
    private val list = ArrayList<Data_artikel>()
    private lateinit var sharedpreferencetoken: sharedpreferencetoken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActifityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedpreferencetoken = sharedpreferencetoken(this)

        setupView()
        binding.fabAdd.setOnClickListener{
            startActivity(Intent(this@Homepage, Upload::class.java))
        }
        artikel = findViewById(R.id.rv_item)
        artikel.setHasFixedSize(true)


        list.addAll(getlistartikel())
        showartikel()


        binding.logout.setOnClickListener{
            val intent = Intent(this@Homepage , welcome::class.java)
            sharedpreferencetoken.clearData()
            startActivity(intent)
            finish()
        }

    }

    private fun showartikel() {
        artikel.layoutManager = LinearLayoutManager(this)
        val Artikteladapter = ArtikelAdapter(list)
        artikel.adapter = Artikteladapter

        Artikteladapter.setOnItemClickCallback(object : ArtikelAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Data_artikel) {
                showdataartikel(data)
            }
        })
    }

    private fun showdataartikel(data: Data_artikel) {
        val moveWithObjectIntent = Intent(this@Homepage, Detail_article::class.java)
        moveWithObjectIntent.putExtra(Detail_article.EXTRA_DATA_ARTIKEL, data)
        startActivity(moveWithObjectIntent)
    }

    private fun getlistartikel(): Collection<Data_artikel> {
        val judulArtikel = resources.getStringArray(R.array.Judul_article)
        val deskripsiArtikel = resources.getStringArray(R.array.deskripsi_1)
        val imageArtikel = resources.obtainTypedArray(R.array.image_artikel)
        val subjudul1 = resources.getStringArray(R.array.sub_judul1)
        val subjudul2 = resources.getStringArray(R.array.sub_judul2)
        val deksripsi2 = resources.getStringArray(R.array.deskripsi_2)
        val deskripsi3 = resources.getStringArray(R.array.deksripsi_3)

        val minLength = minOf(
            judulArtikel.size,
            deskripsiArtikel.size,
            deksripsi2.size,
            deskripsi3.size,
            subjudul1.size,
            subjudul2.size,
            imageArtikel.length()
        )

        val listArtikel = ArrayList<Data_artikel>()
        for (i in 0 until minLength) {
            val artikel = Data_artikel(
                judulArtikel[i],
                deskripsiArtikel[i],
                deksripsi2[i],
                deskripsi3[i],
                subjudul1[i],
                subjudul2[i],
                imageArtikel.getResourceId(i, -1)
            )
            listArtikel.add(artikel)
        }
        imageArtikel.recycle()

        return listArtikel
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