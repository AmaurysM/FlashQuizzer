package com.example.flashquizzer.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.model.AuthManager
import com.example.flashquizzer.model.LoginData
import com.example.flashquizzer.navigation.FlashQuizzerDestinations

class LoginViewmodel : ViewModel() {
    var loginData by mutableStateOf(LoginData("", ""))

    fun login() {
        AuthManager.login(loginData, loginInputEmpty())
    }

    fun loginInputEmpty(): Boolean {
        return loginData.email.isEmpty() || loginData.password.isEmpty()
    }


    fun goHome(navHostController: NavHostController) {
        navHostController.navigate(FlashQuizzerDestinations.Home.route)
    }
}