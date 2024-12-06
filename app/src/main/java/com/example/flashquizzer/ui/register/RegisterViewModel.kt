package com.example.flashquizzer.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.R
import com.example.flashquizzer.model.AuthManager
import com.example.flashquizzer.model.LoginData
import com.example.flashquizzer.model.RegisterData
import com.example.flashquizzer.navigation.FlashQuizzerDestinations

class RegisterViewmodel : ViewModel() {


    var registerData by mutableStateOf(RegisterData())

    var passwordVisible by mutableStateOf(false)
    var confirmPasswordVisible by mutableStateOf(false)

    fun iconPassword(): Int {
        return if (passwordVisible) {
            R.drawable.baseline_visibility_24
        } else {
            R.drawable.baseline_visibility_off_24
        }
    }

    fun iconConfirmPassword(): Int {
        return if (confirmPasswordVisible) {
            R.drawable.baseline_visibility_24
        } else {
            R.drawable.baseline_visibility_off_24
        }
    }

    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun toggleConfirmPasswordVisibility() {
        confirmPasswordVisible = !confirmPasswordVisible
    }

    fun register(navHostController: NavHostController) {
        AuthManager.register(registerData, navHostController)
    }

    fun registerInputEmpty(): Boolean {
        return registerData.email.isEmpty() || registerData.password.isEmpty()
    }

    fun goHome(navHostController: NavHostController) {
        navHostController.navigate(FlashQuizzerDestinations.Home.route)
    }

    fun goToLogin(navHostController: NavHostController) {
        navHostController.navigate(FlashQuizzerDestinations.Login.route)
    }




}