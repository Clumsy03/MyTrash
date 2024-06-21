package dicoding.bangkit.capstone_project.ui.Hasil_klasifikasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HasilKlasifikasiViewModel : ViewModel() {

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    private val _suggestion = MutableLiveData<String>()
    val suggestion: LiveData<String> get() = _suggestion

    fun setClassificationData(result: String, suggestion: String) {
        _result.value = result
        _suggestion.value = suggestion
    }
}