package dicoding.bangkit.capstone_project.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data_artikel(
    val name: String,
    val deksripsi1: String,
    val deksripsi2: String,
    val deksripsi3: String,
    val subjudul1: String,
    val subjudul2: String,
    val image: Int
) : Parcelable
