package gautam.sarthak.textextractor.presentation

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gautam.sarthak.textextractor.repository.MainRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepo
):ViewModel() {

    private var _extractedText = MutableStateFlow("")
    val extractedText = _extractedText.asStateFlow()

    fun getTextFromCapturedImage(bitmap: Bitmap){
        viewModelScope.launch {
            repo.getTextFromCapturedImage(bitmap).collect{
                _extractedText.value=it
            }
        }
    }
    fun getTextFromSelectedImage(uri: Uri){
        viewModelScope.launch {
            repo.getTextFromSelectedImage(uri).collect{
                _extractedText.value=it
            }
        }
    }
    fun copyTextToClipboard(){
        viewModelScope.launch {
            repo.copyTextToClipboard(_extractedText.value)
        }
    }
}