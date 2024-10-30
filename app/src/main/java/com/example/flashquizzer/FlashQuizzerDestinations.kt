package com.example.flashquizzer

sealed class FlashQuizzerDestinations(val route: String) {
    data object Home : FlashQuizzerDestinations("home")
    data object UploadDoc : FlashQuizzerDestinations("upload_doc")
    data object ViewFlashcards : FlashQuizzerDestinations("view_flashcards")
    data object TakeQuiz : FlashQuizzerDestinations("take_quiz")
    data object Login : FlashQuizzerDestinations("login")

}