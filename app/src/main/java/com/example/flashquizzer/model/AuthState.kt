package com.example.flashquizzer.model

import com.example.flashquizzer.navigation.FlashQuizzerDestinations

sealed class AuthState(val destination: FlashQuizzerDestinations? = null) {
    object Authenticated : AuthState( FlashQuizzerDestinations.Home)
    object Unauthenticated : AuthState( FlashQuizzerDestinations.Login)
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}