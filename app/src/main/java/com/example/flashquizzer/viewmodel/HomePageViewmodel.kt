package com.example.flashquizzer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flashquizzer.FlashQuizzerDestinations
import androidx.navigation.NavHostController

class HomePageViewmodel: ViewModel() {
    fun goUploadDoc(navController: NavHostController) {
        navController.navigate(FlashQuizzerDestinations.UploadDoc.route)
    }

    fun goCreateFlashcards(navController: NavHostController) {
        navController.navigate(FlashQuizzerDestinations.ViewFlashcards.route)
    }

    fun goTakeQuiz(navController: NavHostController) {
        navController.navigate(FlashQuizzerDestinations.TakeQuiz.route)
    }

    fun goLogin(navController: NavHostController) {
        navController.navigate(FlashQuizzerDestinations.Login.route)
    }

    fun goRegister(navController: NavHostController) {
        //TODO("Not yet implemented")
    }

}