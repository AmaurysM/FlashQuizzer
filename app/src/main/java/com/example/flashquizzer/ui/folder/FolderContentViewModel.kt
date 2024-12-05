package com.example.flashquizzer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashquizzer.model.Card
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FolderContentViewModel : ViewModel() {
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val _flashcards = MutableStateFlow<List<Card>>(emptyList())
    val flashcards: StateFlow<List<Card>> = _flashcards

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadFlashcards(folderId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            // Handle unauthenticated user
            Log.e("FolderContentViewModel", "User not authenticated")
            return
        }
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val snapshot = firebaseFirestore.collection("users").document(userId)
                    .collection("folders").document(folderId)
                    .collection("flashcards")
                    .get()
                    .await()

                val flashcardsList = snapshot.documents.mapNotNull { document ->
                    val question = document.getString("question")
                    val answer = document.getString("answer")
                    if (question != null && answer != null) {
                        Card(question = question, answer = answer)
                    } else {
                        null
                    }
                }
                _flashcards.value = flashcardsList
            } catch (e: Exception) {
                Log.e("FolderContentViewModel", "Error loading flashcards", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
