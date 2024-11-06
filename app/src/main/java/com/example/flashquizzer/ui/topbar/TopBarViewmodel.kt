package com.example.flashquizzer.ui.topbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.navigation.FlashQuizzerDestinations

class TopBarViewmodel : ViewModel() {

    private var title by mutableStateOf("")

    val topBarTitle: (NavHostController) -> String = { navController ->
        title = when (navController.currentBackStackEntry?.destination?.route) {
            FlashQuizzerDestinations.Home.route -> "Home"
            FlashQuizzerDestinations.UploadDoc.route -> "Upload Document"
            FlashQuizzerDestinations.ViewFlashcards.route -> "Flashcards"
            FlashQuizzerDestinations.TakeQuiz.route -> "Quiz"
            FlashQuizzerDestinations.Login.route -> "Login"
            FlashQuizzerDestinations.Register.route -> "Register"
            else -> ""
        }
        title
    }
    }
