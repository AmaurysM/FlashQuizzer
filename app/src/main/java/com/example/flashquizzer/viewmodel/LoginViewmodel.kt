package com.example.flashquizzer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController


data class LoginData(var username: String, var password: String)

class LoginViewmodel: ViewModel() {
    fun login(loginData: LoginData) {

    }

    fun goHome(navHostController: NavHostController) {

    }

    var loginData by mutableStateOf(LoginData("", ""))

}