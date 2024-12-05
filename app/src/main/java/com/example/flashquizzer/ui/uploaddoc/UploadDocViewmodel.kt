import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.InputStream

class UploadDocViewModel : ViewModel() {
    private val _documentContent = MutableStateFlow<String?>(null)
    val documentContent: StateFlow<String?> = _documentContent.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun extractTextFromFile(context: Context, uri: Uri) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val mimeType = context.contentResolver.getType(uri) ?: ""
                val inputStream = context.contentResolver.openInputStream(uri)

                val extractedText = when {
                    mimeType.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document") -> extractTextFromDocx(inputStream!!)
                    mimeType.contains("application/vnd.openxmlformats-officedocument.presentationml.presentation") -> extractTextFromPptx(inputStream!!)
                    else -> "Unsupported file type."
                }

                _documentContent.value = extractedText
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error reading document: ${e.message}"
                Log.e("UploadDocViewModel", "Error reading document", e)
            } finally {
                _isLoading.value = false
            }
        }
    }


    private fun extractTextFromDocx(inputStream: InputStream): String {
        XWPFDocument(inputStream).use { docx ->
            val textBuilder = StringBuilder()
            for (paragraph in docx.paragraphs) {
                textBuilder.append(paragraph.text).append("\n")
            }
            return textBuilder.toString()
        }
    }



    private fun extractTextFromPptx(inputStream: InputStream): String {
        XMLSlideShow(inputStream).use { pptx ->
            val textBuilder = StringBuilder()
            for (slide in pptx.slides) {
                for (shape in slide.shapes) {
                    if (shape is org.apache.poi.xslf.usermodel.XSLFTextShape) {
                        textBuilder.append(shape.text).append("\n")
                    }
                }
            }
            return textBuilder.toString()
        }
    }
}
