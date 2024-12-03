package com.example.flashquizzer.ui.uploaddoc

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    private val firestore = FirebaseFirestore.getInstance()

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
                ensureUserCollectionExistsAndUpload(stringBuilder.toString())
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error reading document: ${e.message}"
                Log.e("UploadDocViewModel", "Error reading document", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun ensureUserCollectionExistsAndUpload(content: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            _error.value = "User not logged in"
            return
        }

        val userId = currentUser.uid
        val userDocRef = firestore.collection("users").document(userId)

        userDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                // If user document doesn't exist, create it
                userDocRef.set(mapOf("initialized" to true)).addOnSuccessListener {
                    Log.d("UploadDocViewModel", "User collection created")
                    uploadTextToFirebase(userId, content)
                }.addOnFailureListener { e ->
                    _error.value = "Failed to create user collection: ${e.message}"
                }
            } else {
                uploadTextToFirebase(userId, content)
            }
        }.addOnFailureListener { e ->
            _error.value = "Error checking user collection: ${e.message}"
        }
    }



    private fun uploadTextToFirebase(userId: String, content: String) {

        val storageRef = Firebase.storage.reference
        val fileName = "document_${System.currentTimeMillis()}.txt"
        val docRef = storageRef.child("uploads/$userId/documents/$fileName")
        val contentBytes = content.toByteArray(Charset.forName("UTF-8"))

        docRef.putBytes(contentBytes)
            .addOnSuccessListener {
                Log.d("UploadDocViewModel", "Document uploaded successfully")
                // Fetch download URL after a successful upload
                docRef.downloadUrl.addOnSuccessListener { uri ->
                    Log.d("UploadDocViewModel", "Download URL: $uri")
                    val documentMetadata = mapOf(
                        "fileName" to fileName,
                        "downloadUrl" to uri.toString(),
                        "timestamp" to System.currentTimeMillis()
                    )
                    firestore.collection("users")
                        .document(userId)
                        .collection("documents") // Collection under each user to store documents metadata
                        .add(documentMetadata)
                        .addOnSuccessListener {
                            Log.d("UploadDocViewModel", "Document metadata saved to Firestore")
                        }
                        .addOnFailureListener { e ->
                            _error.value = "Failed to save metadata: ${e.message}"
                            Log.e("UploadDocViewModel", "Failed to save metadata", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                _error.value = "Upload failed: ${e.message}"
            }
    }

    fun downloadTextFromFirebase() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            _error.value = "User not logged in"
            return
        }

        val userId = currentUser.uid
        val storageRef = Firebase.storage.reference
        val documentsRef = storageRef.child("uploads/$userId/documents/")


        // Download up to 1MB of file content (adjust size if needed)
        documentsRef.listAll()
            .addOnSuccessListener { listResult ->
                if (listResult.items.isNotEmpty()) {
                    val latestFileRef = listResult.items.last()
                    latestFileRef.getBytes(1024 * 1024)
                        .addOnSuccessListener { bytes ->
                            val content = String(bytes, Charset.forName("UTF-8"))
                            Log.d("UploadDocViewModel", "Downloaded content: $content")
                            _documentContent.value = content
                        }
                        .addOnFailureListener { e ->
                            _error.value = "Download failed: ${e.message}"
                            Log.e("UploadDocViewModel", "Download failed", e)
                        }
                } else {
                    _error.value = "No documents found for this user."
                }
            }
            .addOnFailureListener { e ->
                _error.value = "Failed to list documents: ${e.message}"
                Log.e("UploadDocViewModel", "Failed to list documents", e)
            }
    }
}


