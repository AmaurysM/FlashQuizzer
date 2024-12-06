package com.example.flashquizzer.navigation

import com.example.flashquizzer.R

// Sealed class to represent the destinations in the app
sealed class FlashQuizzerDestinations(val route: String, val title: String = "", val icon: Int = 0) {
    data object Splash : FlashQuizzerDestinations("splash")
    data object ViewFlashcards : FlashQuizzerDestinations("view_flashcards")
    data object TakeQuiz : FlashQuizzerDestinations("take_quiz")
    data object Login : FlashQuizzerDestinations("login")
    data object Register : FlashQuizzerDestinations("register")
    data object SelectFlashcards : FlashQuizzerDestinations("selectFlashcards")

    data object Home : FlashQuizzerDestinations("home", "Home", R.drawable.baseline_home_filled_24)
    data object UploadDoc : FlashQuizzerDestinations("upload_doc", "Upload", R.drawable.baseline_file_upload)
    data object Profile : FlashQuizzerDestinations("profile", "Profile", R.drawable.baseline_account_circle_24)
}


val bottomBarDestinations = listOf(FlashQuizzerDestinations.Home, FlashQuizzerDestinations.UploadDoc, FlashQuizzerDestinations.Profile)

