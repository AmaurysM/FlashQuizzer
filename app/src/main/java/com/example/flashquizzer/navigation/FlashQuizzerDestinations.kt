package com.example.flashquizzer.navigation



sealed class FlashQuizzerDestinations(val route: String) {
    object Home : FlashQuizzerDestinations("home")
    object UploadDoc : FlashQuizzerDestinations("upload_doc")
    object ViewFlashcards : FlashQuizzerDestinations("view_flashcards")
    object TakeQuiz : FlashQuizzerDestinations("take_quiz")
    object Login : FlashQuizzerDestinations("login")
    object Register : FlashQuizzerDestinations("register")}