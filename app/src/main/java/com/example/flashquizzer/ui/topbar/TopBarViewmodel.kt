package com.example.flashquizzer.ui.topbar

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.navigation.FlashQuizzerDestinations

class TopBarViewmodel: ViewModel() {



    val topBarTitle: (NavHostController) -> String = { navController ->
        when (navController.currentBackStackEntry?.destination?.route) {
            FlashQuizzerDestinations.Home.route -> FlashQuizzerDestinations.Home.title
            FlashQuizzerDestinations.UploadDoc.route -> FlashQuizzerDestinations.UploadDoc.title
            FlashQuizzerDestinations.ViewFlashcards.route -> FlashQuizzerDestinations.ViewFlashcards.title
            FlashQuizzerDestinations.TakeQuiz.route -> FlashQuizzerDestinations.TakeQuiz.title
            FlashQuizzerDestinations.Login.route -> FlashQuizzerDestinations.Login.title
            FlashQuizzerDestinations.Register.route -> FlashQuizzerDestinations.Register.title

            else -> {
                ""
            }
        }
    }
}