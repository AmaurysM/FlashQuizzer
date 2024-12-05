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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UploadDocViewModel : ViewModel() {
    private val _flashcards = MutableStateFlow<List<String>>(emptyList())
    val flashcards: StateFlow<List<String>> = _flashcards.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(OpenAIService::class.java)

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

                generateFlashcards(extractedText)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error reading document: ${e.message}"
                Log.e("UploadDocViewModel", "Error reading document", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun generateFlashcards(content: String) {
        try {
            val request = OpenAIRequest(
                model = "gpt-4",
                messages = listOf(
                    Message(role = "system", content = "You are an assistant that generates flashcards from provided text."),
                    Message(role = "user", content = """
            Given the following text, generate 2 flashcards in the following format:

            Question: [Your question here]
            Answer: [Your answer here]

            The flashcards should be related to the key concepts from the provided text.

            Text: 
            $content
        """)
                ),
                max_tokens = 150,
                temperature = 0.7
            )

            val response = service.generateFlashcards(request)
            val flashcards = response.choices.first().message.content.trim().split('\n')
            _flashcards.value = flashcards

        } catch (e: Exception) {
            _error.value = "Error generating flashcards: ${e.message}"
            Log.e("UploadDocViewModel", "Error generating flashcards", e)
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
