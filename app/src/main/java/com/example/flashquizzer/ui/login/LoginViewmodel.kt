package com.example.flashquizzer.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.R
import com.example.flashquizzer.model.AuthManager
import com.example.flashquizzer.model.LoginData
import com.example.flashquizzer.navigation.FlashQuizzerDestinations

class LoginViewmodel : ViewModel() {
    var passwordVisible by mutableStateOf(false)
    var loginData by mutableStateOf(LoginData("", ""))

    fun iconPassword(): Int {
        return if (passwordVisible) {
            R.drawable.baseline_visibility_24
        } else {
            R.drawable.baseline_visibility_off_24
        }
    }


    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun login() {
        AuthManager.login(loginData, loginInputEmpty())
    }

    fun loginInputEmpty(): Boolean {
        return loginData.email.isEmpty() || loginData.password.isEmpty()
    }


    fun goHome(navHostController: NavHostController) {
        navHostController.navigate(FlashQuizzerDestinations.Home.route)
    }

    fun goToRegister(navHostController: NavHostController) {
        navHostController.navigate(FlashQuizzerDestinations.Register.route)
    }
}