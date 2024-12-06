package com.example.flashquizzer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashquizzer.model.Flashcard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ViewFlashcardsViewModel : ViewModel() {
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val _flashcards = MutableStateFlow<List<Flashcard>>(emptyList())
    val flashcards: StateFlow<List<Flashcard>> = _flashcards

    init {
        loadFlashcards()
    }

    private fun loadFlashcards() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        viewModelScope.launch {
            try {
                // Replace "selectedFolderId" with the actual folder ID you want to load flashcards from
                val selectedFolderId = "your_folder_id_here" // Replace with the actual folder ID, can be updaed
                val snapshot = firebaseFirestore.collection("users").document(userId) // Update the path to the folder
                    .collection("folders").document(selectedFolderId)
                    .collection("flashcards")
                    .get()
                    .await()

                val flashcardsList = snapshot.documents.mapNotNull { document -> // Map the documents to a list of flashcards.
                    val question = document.getString("question")
                    val answer = document.getString("answer")
                    if (question != null && answer != null) {
                        Flashcard(question = question, answer = answer)
                    } else {
                        null
                    }
                }
                _flashcards.value = flashcardsList
            } catch (e: Exception) {
                Log.e("ViewFlashcardsViewModel", "Error loading flashcards", e)
            }
        }
    }
}
