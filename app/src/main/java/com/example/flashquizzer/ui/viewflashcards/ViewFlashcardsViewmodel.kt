package com.example.flashquizzer.ui.viewflashcards

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.flashquizzer.model.Flashcard

class ViewFlashcardsViewmodel : ViewModel() {
    fun getFlashcards(): List<Flashcard> {
        // TODO: Implement getting flashcards from firebase
        return listOf(
            Flashcard("What is the capital of France?", "Paris")
            , Flashcard("What is the capital of Germany?", "Berlin")
        )
    }

    fun flipCard(it: Flashcard, currentSide: MutableState<String>) {
        if (currentSide.value == it.front) {
            currentSide.value = it.back
        } else {
            currentSide.value = it.front
        }
        /*if (it.currentSide == it.front) {
            it.currentSide = it.back
        } else {
            it.currentSide = it.front
        }*/
    }
}