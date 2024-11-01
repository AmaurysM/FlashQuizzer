package com.example.flashquizzer.ui.homepage

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.navigation.FlashQuizzerDestinations
import com.example.flashquizzer.model.AuthManager


class HomePageViewmodel : ViewModel() {

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
        navController.navigate(FlashQuizzerDestinations.Register.route)
    }

    fun logout() {
        AuthManager.logout()
    }
}