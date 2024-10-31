package com.example.flashquizzer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.FlashQuizzerDestinations
import com.example.flashquizzer.model.AuthManager
import com.example.flashquizzer.model.LoginData

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