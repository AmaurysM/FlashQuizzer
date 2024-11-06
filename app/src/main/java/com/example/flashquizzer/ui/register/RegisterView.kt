package com.example.flashquizzer.ui.register

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.model.AuthManager
import com.example.flashquizzer.model.AuthState
import com.example.flashquizzer.navigation.FlashQuizzerDestinations

@Composable
fun RegisterView(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: RegisterViewmodel = viewModel()
) {

    val contextForToast = LocalContext.current.applicationContext
    val authState = AuthManager.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navHostController.navigate(FlashQuizzerDestinations.Home.route)
            is AuthState.Error -> Toast.makeText(
                contextForToast,
                (AuthManager.authState.value as AuthState.Error).message,
                Toast.LENGTH_LONG
            ).show()

            else -> Unit
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.registerData.email,
            onValueChange = { newEmail: String ->
                viewModel.registerData = viewModel.registerData.copy(email = newEmail)
            },
            label = { Text(text = "Email") }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.registerData.password,
            onValueChange = { newPassword: String ->
                viewModel.registerData = viewModel.registerData.copy(password = newPassword)
            },
            label = { Text(text = "Password") }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                viewModel.goHome(navHostController)
            }) {
                Text(text = "Return")
            }

            Button(onClick = {
                viewModel.register()
            }) {
                Text(text = "Register")
            }
        }
    }
}