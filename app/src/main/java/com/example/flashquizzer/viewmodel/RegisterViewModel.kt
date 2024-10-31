package com.example.flashquizzer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.FlashQuizzerDestinations
import com.example.flashquizzer.model.AuthManager
import com.example.flashquizzer.model.RegisterData

class RegisterViewmodel : ViewModel() {

    var registerData by mutableStateOf(RegisterData("", ""))

    fun register() {
        AuthManager.register(registerData, registerInputEmpty())
    }

    fun registerInputEmpty(): Boolean {
        return registerData.email.isEmpty() || registerData.password.isEmpty()
    }

    fun goHome(navHostController: NavHostController) {
        navHostController.navigate(FlashQuizzerDestinations.Home.route)
    }
}