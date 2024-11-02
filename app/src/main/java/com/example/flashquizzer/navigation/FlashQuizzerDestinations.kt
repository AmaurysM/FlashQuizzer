package com.example.flashquizzer.navigation

sealed class FlashQuizzerDestinations(val route: String, val title: String = "") {
    data object Home : FlashQuizzerDestinations("home")
    data object UploadDoc : FlashQuizzerDestinations("upload_doc")
    data object ViewFlashcards : FlashQuizzerDestinations("view_flashcards", "Flashcards")
    data object TakeQuiz : FlashQuizzerDestinations("take_quiz", "Quiz")
    data object Login : FlashQuizzerDestinations("login")
    data object Register : FlashQuizzerDestinations("register")

}