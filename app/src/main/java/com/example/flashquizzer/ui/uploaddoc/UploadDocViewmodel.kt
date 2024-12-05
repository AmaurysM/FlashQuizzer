package com.example.flashquizzer.viewmodel

import Message
import OpenAIRequest
import OpenAIService
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashquizzer.model.Flashcard
import com.example.flashquizzer.model.FolderDC
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xwpf.usermodel.XWPFDocument
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream

class UploadDocViewModel : ViewModel() {

    private val _folders = MutableStateFlow<List<FolderDC>>(emptyList())
    val folders: StateFlow<List<FolderDC>> = _folders.asStateFlow()

    private val _unreviewedFlashcards = MutableStateFlow<MutableList<String>>(mutableListOf())
    val unreviewedFlashcards: StateFlow<List<String>> = _unreviewedFlashcards.asStateFlow()

    private val _selectedFlashcards = MutableStateFlow<MutableList<String>>(mutableListOf())
    val selectedFlashcards: StateFlow<List<String>> = _selectedFlashcards.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    var selectedFolderId: String? = null

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(OpenAIService::class.java)

    init {
        fetchFoldersFromFirebase()
    }

    private fun fetchFoldersFromFirebase() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        viewModelScope.launch {
            try {
                val snapshot = firebaseFirestore.collection("users").document(userId)
                    .collection("folders")
                    .get()
                    .await()

                val foldersList = snapshot.documents.mapNotNull { document ->
                    val name = document.getString("name") ?: return@mapNotNull null
                    val id = document.id
                    FolderDC(name = name, id = id)
                }
                _folders.value = foldersList
            } catch (e: Exception) {
                Log.e("UploadDocViewModel", "Error fetching folders", e)
            }
        }
    }

    fun extractTextFromFile(context: Context, uri: Uri) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val mimeType = context.contentResolver.getType(uri) ?: ""
                val inputStream = context.contentResolver.openInputStream(uri)

                val extractedText = when {
                    mimeType.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document") -> extractTextFromDocx(
                        inputStream!!
                    )

                    mimeType.contains("application/vnd.openxmlformats-officedocument.presentationml.presentation") -> extractTextFromPptx(
                        inputStream!!
                    )

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
                    Message(
                        role = "system",
                        content = "You are an assistant that generates flashcards from provided text."
                    ),
                    Message(
                        role = "user", content = """
                    Given the following text, generate 3 flashcards in the following format:

                    FlashCard #:
                    Question: [Your question here]
                    Answer: [Your answer here]

                    The flashcards should be related to the key concepts from the provided text. FlashCard #: should be incremented and separate each card

                    Text:
                    $content
                """.trimIndent()
                    )
                ),
                max_tokens = 150,
                temperature = 0.7
            )

            val response = service.generateFlashcards(request)
            val responseContent = response.choices.first().message.content.trim()

            Log.d("UploadDocViewModel", "OpenAI API response: $responseContent")

            val flashcards = parseFlashcards(responseContent)
            _unreviewedFlashcards.value = flashcards.toMutableList()
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    "HTTP error ${e.code()}: $errorBody"
                }

                else -> e.toString()
            }
            _error.value = "Error generating flashcards: $errorMessage"
            Log.e("UploadDocViewModel", "Error generating flashcards", e)
        }
    }

    private fun parseFlashcards(responseContent: String): List<String> {
        val flashcardBlocks = responseContent.split(Regex("FlashCard #\\d+:"))
            .mapNotNull { it.trim().takeIf { it.isNotEmpty() } }

        val flashcards = mutableListOf<String>()

        for (block in flashcardBlocks) {
            val questionMatch = Regex("Question: (.+)").find(block)
            val answerMatch = Regex("Answer: (.+)").find(block)
            if (questionMatch != null && answerMatch != null) {
                val question = questionMatch.groupValues[1].trim()
                val answer = answerMatch.groupValues[1].trim()
                val flashcard = "Question: $question\nAnswer: $answer"
                flashcards.add(flashcard)
            } else {
                Log.e("UploadDocViewModel", "Failed to parse flashcard block: $block")
            }
        }

        return flashcards
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

    fun addCustomFlashcard(flashcard: String) {
        _unreviewedFlashcards.value.add(flashcard)
    }

    fun confirmFlashcard(flashcard: String) {
        val selectedList = _selectedFlashcards.value.toMutableList()
        val unreviewedList = _unreviewedFlashcards.value.toMutableList()

        if (!selectedList.contains(flashcard)) {
            selectedList.add(flashcard)
        }
        unreviewedList.remove(flashcard)

        _selectedFlashcards.value = selectedList
        _unreviewedFlashcards.value = unreviewedList
    }

    fun rejectFlashcard(flashcard: String) {
        val unreviewedList = _unreviewedFlashcards.value.toMutableList()
        unreviewedList.remove(flashcard)
        _unreviewedFlashcards.value = unreviewedList
    }

    suspend fun saveFlashcardsToFirebase() {
        _isLoading.value = true
        try {
            val flashcardsToSave = _selectedFlashcards.value
            val folderId = selectedFolderId
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId == null) {
                _error.value = "User not authenticated."
                _isLoading.value = false
                return
            }
            if (folderId != null) {
                val folderRef = firebaseFirestore.collection("users").document(userId)
                    .collection("folders").document(folderId)
                val flashcardsData = flashcardsToSave.mapNotNull { flashcardString ->
                    val flashcard = parseFlashcardString(flashcardString)
                    flashcard?.let {
                        mapOf(
                            "question" to it.question,
                            "answer" to it.answer
                        )
                    }
                }
                // Save each flashcard as a separate document
                for (flashcard in flashcardsData) {
                    folderRef.collection("flashcards")
                        .add(flashcard)
                        .await()
                }
                Log.d("UploadDocViewModel", "Flashcards saved to folder!")
            } else {
                _error.value = "No folder selected."
            }
        } catch (e: Exception) {
            _error.value = "Error saving flashcards: ${e.message}"
            Log.e("UploadDocViewModel", "Error saving flashcards", e)
        } finally {
            _isLoading.value = false
        }
    }

    private fun parseFlashcardString(flashcardString: String): Flashcard? {
        val questionMatch = Regex("Question: (.+)").find(flashcardString)
        val answerMatch = Regex("Answer: (.+)").find(flashcardString)
        return if (questionMatch != null && answerMatch != null) {
            Flashcard(
                question = questionMatch.groupValues[1].trim(),
                answer = answerMatch.groupValues[1].trim()
            )
        } else {
            null
        }
    }
}
