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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.viewmodel.LoginViewmodel
import com.example.flashquizzer.viewmodel.RegisterViewmodel

@Composable
fun RegisterView(
    navHostController: NavHostController = rememberNavController()
    , modifier: Modifier = Modifier
    , viewModel: RegisterViewmodel = viewModel()
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
        , horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
            , value = viewModel.registerData.username
            , onValueChange = {viewModel.registerData = viewModel.registerData.copy(username = it)}
            , label = { Text(text = "Username")}
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
            , value = viewModel.registerData.password
            , onValueChange = {viewModel.registerData = viewModel.registerData.copy(password = it)}
            , label = { Text(text = "Password")}
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
            , value = viewModel.registerData.email
            , onValueChange = {viewModel.registerData = viewModel.registerData.copy(email = it)}
            , label = { Text(text = "Email")}
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
            , value = viewModel.registerData.phoneNumber
            , onValueChange = {viewModel.registerData = viewModel.registerData.copy(phoneNumber = it)}
            , label = { Text(text = "Phone Number")}
        )

        Row(modifier = Modifier.fillMaxWidth()
        , horizontalArrangement = Arrangement.SpaceBetween
        , verticalAlignment = Alignment.CenterVertically
        ){
            Button(onClick = {
                viewModel.goHome(navHostController)
            }) {
                Text(text = "Return")
            }

            Button(onClick = {
                viewModel.goHome(navHostController)
            }) {
                Text(text = "Register")
            }
        }
    }
}