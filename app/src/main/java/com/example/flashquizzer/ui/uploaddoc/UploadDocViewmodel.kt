package com.example.flashquizzer.ui.uploaddoc

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset

class UploadDocViewModel : ViewModel() {
    private val _documentContent = MutableStateFlow<String?>(null)
    val documentContent: StateFlow<String?> = _documentContent.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun readTextAndUploadToFirebase(context: Context, uri: Uri) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("UploadDocViewModel", "Reading URI: $uri")  // Log URI
                val stringBuilder = StringBuilder()
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8"))).use { reader ->
                        var line: String? = reader.readLine()
                        while (line != null) {
                            stringBuilder.append(line).append('\n')
                            line = reader.readLine()
                        }
                    }
                }
                Log.d("UploadDocViewModel", "File Content: ${stringBuilder.toString()}")  // Log content
                _documentContent.value = stringBuilder.toString()
                uploadTextToFirebase(stringBuilder.toString())
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error reading document: ${e.message}"
                Log.e("UploadDocViewModel", "Error reading document", e)
            } finally {
                _isLoading.value = false
            }
        }
    }


    private fun uploadTextToFirebase(content: String) {
        val storageRef = Firebase.storage.reference
        val docRef = storageRef.child("documents/test_document.txt")
        val contentBytes = content.toByteArray(Charset.forName("UTF-8"))

        docRef.putBytes(contentBytes)
            .addOnSuccessListener {
                Log.d("UploadDocViewModel", "Document uploaded successfully")
                // Fetch download URL after a successful upload
                docRef.downloadUrl.addOnSuccessListener { uri ->
                    Log.d("UploadDocViewModel", "Download URL: $uri")
                    // Save or display the URL as needed
                    // Example: _documentUrl.value = uri.toString()
                }
            }
            .addOnFailureListener { e ->
                _error.value = "Upload failed: ${e.message}"
            }
    }

    fun downloadTextFromFirebase() {
        val storageRef = Firebase.storage.reference
        val docRef = storageRef.child("documents/test_document.txt")

        // Download up to 1MB of file content (adjust size if needed)
        docRef.getBytes(1024 * 1024)
            .addOnSuccessListener { bytes ->
                val content = String(bytes, Charset.forName("UTF-8"))
                Log.d("UploadDocViewModel", "Downloaded content: $content")
                // Process or display content as needed
                _documentContent.value = content
            }
            .addOnFailureListener { e ->
                _error.value = "Download failed: ${e.message}"
            }
    }

}


