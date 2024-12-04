package com.example.flashquizzer.ui.uploaddoc

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.*

data class Document(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val content: String,
    val uploadDate: Long = System.currentTimeMillis()
)

class UploadDocViewModel : ViewModel() {
    private val _documents = MutableStateFlow<List<Document>>(emptyList())
    val documents: StateFlow<List<Document>> = _documents.asStateFlow()

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
                val fileName = getFileName(context, uri) ?: "unknown_document.txt"
                val content = readDocumentContent(context, uri)

                val document = Document(
                    name = fileName,
                    content = content
                )

                uploadDocumentToFirebase(document)
                _documents.value = _documents.value + document
                _documentContent.value = content
                _error.value = null

            } catch (e: Exception) {
                _error.value = "Error reading document: ${e.message}"
                Log.e("UploadDocViewModel", "Error reading document", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            val nameIndex = it.getColumnIndex("_display_name")
            if (it.moveToFirst() && nameIndex != -1) {
                it.getString(nameIndex)
            } else null
        }
    }

    private fun readDocumentContent(context: Context, uri: Uri): String {
        return context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8"))).use { reader ->
                reader.readText()
            }
        } ?: throw IllegalStateException("Could not read document content")
    }

    private fun uploadDocumentToFirebase(document: Document) {
        val storageRef = Firebase.storage.reference
        val docRef = storageRef.child("documents/${document.id}_${document.name}")
        val contentBytes = document.content.toByteArray(Charset.forName("UTF-8"))

        docRef.putBytes(contentBytes)
            .addOnSuccessListener {
                Log.d("UploadDocViewModel", "Document uploaded successfully")
                docRef.downloadUrl.addOnSuccessListener { uri ->
                    Log.d("UploadDocViewModel", "Download URL: $uri")
                }
            }
            .addOnFailureListener { e ->
                _error.value = "Upload failed: ${e.message}"
            }
    }

    fun deleteDocument(documentId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val storageRef = Firebase.storage.reference
                val document = _documents.value.find { it.id == documentId }

                document?.let {
                    val docRef = storageRef.child("documents/${it.id}_${it.name}")
                    docRef.delete()
                        .addOnSuccessListener {
                            _documents.value = _documents.value.filter { doc -> doc.id != documentId }
                            _error.value = null
                        }
                        .addOnFailureListener { e ->
                            _error.value = "Delete failed: ${e.message}"
                        }
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
