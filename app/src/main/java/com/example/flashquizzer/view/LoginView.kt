package com.example.flashquizzer.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.viewmodel.LoginViewmodel

@Preview(showBackground = true)
@Composable
fun LoginView(
    navHostController: NavHostController = rememberNavController()
    , modifier: Modifier = Modifier
    , loginViewModel: LoginViewmodel = viewModel()
){
    Column(
        modifier = modifier.fillMaxWidth()
        .padding(16.dp)
    , horizontalAlignment = Alignment.CenterHorizontally
    , verticalArrangement = Arrangement.Center){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
            , value = loginViewModel.loginData.username
            , onValueChange = {loginViewModel.loginData = loginViewModel.loginData.copy(username = it)}
            , label = { Text(text = "Username")}
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
            , value = loginViewModel.loginData.password
            , onValueChange = {loginViewModel.loginData = loginViewModel.loginData.copy(password = it)}
            , label = { Text(text = "Password")}
        )
        Row(modifier = Modifier.fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceBetween
            , verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                loginViewModel.goHome(navHostController)
            }) {
                Text(text = "Return")
            }

            Button(onClick = {
                loginViewModel.login(loginViewModel.loginData)
            }) {
                Text(text = "Login")
            }
        }

    }
}