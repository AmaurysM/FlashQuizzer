package com.example.flashquizzer.ui.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.flashquizzer.model.AuthManager
import com.example.flashquizzer.model.AuthState
import com.example.flashquizzer.navigation.FlashQuizzerDestinations
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn


class HomePageViewmodel : ViewModel() {
    private val _authState = AuthManager.authState.asFlow()
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthState.Unauthenticated)

    val authState: StateFlow<AuthState> = _authState

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