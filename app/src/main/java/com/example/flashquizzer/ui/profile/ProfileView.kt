package com.example.flashquizzer.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileView(
    navHostController: NavHostController = rememberNavController()
    , modifier: Modifier = Modifier
    , viewModel: ProfileViewmodel = viewModel()
){
    Column(
        modifier = modifier.fillMaxSize()
        , horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            viewModel.logout(navHostController)
        }) {
            Text(text = "Logout")
        }
    }
}