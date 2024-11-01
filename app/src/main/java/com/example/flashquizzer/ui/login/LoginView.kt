package com.example.flashquizzer.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.navigation.FlashQuizzerDestinations
import com.example.flashquizzer.model.AuthManager
import com.example.flashquizzer.model.AuthState

@Preview(showBackground = true)
@Composable
fun LoginView(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: LoginViewmodel = viewModel()
) {
    val authState = AuthManager.authState.observeAsState()
    val contextForToast = LocalContext.current.applicationContext

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navHostController.navigate(FlashQuizzerDestinations.Home.route)
            is AuthState.Error -> Toast.makeText(
                contextForToast,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_LONG
            ).show()

            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.loginData.email,
            onValueChange = { newEmail: String -> viewModel.loginData = viewModel.loginData.copy(email = newEmail) },
            label = { Text( text = "Email")}
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.loginData.password,
            onValueChange = { newPassword: String -> viewModel.loginData = viewModel.loginData.copy(password = newPassword) },
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
                viewModel.login()
            }) {
                Text(text = "Login")
            }
        }

    }
}