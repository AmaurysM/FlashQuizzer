package com.example.flashquizzer.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.model.AuthManager

@Preview(showBackground = true)
@Composable
fun LoginView(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: LoginViewmodel = viewModel()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text( text = "Welcome!"
            , style = MaterialTheme.typography.headlineLarge
            , fontWeight = FontWeight.Bold
            , modifier = Modifier.padding(vertical = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
        ){

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.loginData.email,
                onValueChange = { newEmail: String ->
                    viewModel.loginData = viewModel.loginData.copy(email = newEmail)
                },
                label = { Text(text = "Email Address") }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.loginData.password,
                onValueChange = { newPassword: String ->
                    viewModel.loginData = viewModel.loginData.copy(password = newPassword)
                }
                , label = { Text(text = "Password") }
                , trailingIcon = {
                    IconButton(onClick = {
                        viewModel.togglePasswordVisibility()
                    }) {
                        Icon(painter = painterResource(id = viewModel.iconPassword()) , contentDescription = "Visibility")
                    }
                }
                , visualTransformation = if (viewModel.passwordVisible) {
                    VisualTransformation.None
                } else{
                    PasswordVisualTransformation()
                }
                , keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
            Button(onClick = {
                viewModel.login(navHostController)
            }
                , modifier = Modifier.fillMaxWidth()
                , shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Login")
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        viewModel.goToRegister(navHostController)
                    }
                , horizontalArrangement = Arrangement.Center
                , verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Not a member? ", color = MaterialTheme.colorScheme.onSurface)
                Text(text = "Register now", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}


