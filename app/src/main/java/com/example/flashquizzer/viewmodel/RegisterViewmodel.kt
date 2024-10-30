package com.example.flashquizzer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

data class RegisterData(var username: String, var password: String, var email: String, var phoneNumber: String)
class RegisterViewmodel: ViewModel() {
    fun goHome(navHostController: NavHostController) {

    }

    var registerData by mutableStateOf(RegisterData("", "", "", ""))
}