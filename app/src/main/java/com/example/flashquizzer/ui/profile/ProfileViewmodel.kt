package com.example.flashquizzer.ui.profile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.model.AuthManager
import com.example.flashquizzer.navigation.FlashQuizzerDestinations

class ProfileViewmodel: ViewModel() {
    //val authState = AuthManager.authState
    //val contextForToast = LocalContext.current.applicationContext
    fun logout(navHostController: NavHostController) {
        AuthManager.logout()
        navHostController.navigate(FlashQuizzerDestinations.Login.route)
    }
}