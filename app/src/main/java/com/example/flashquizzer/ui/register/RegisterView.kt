package com.example.flashquizzer.ui.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun RegisterView(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: RegisterViewmodel = viewModel()
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(text = "Sign up"
                , style = MaterialTheme.typography.headlineMedium
                , fontWeight = FontWeight.Bold)

            Text(text = "Create an account to get started"
                , style = MaterialTheme.typography.bodySmall
                , color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Column {
            Text(text = "Name"
                , style = MaterialTheme.typography.bodySmall
                , fontWeight = FontWeight.Bold
                , modifier = Modifier.padding(bottom = 5.dp)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.registerData.username,
                onValueChange = { newUsername: String ->
                    viewModel.registerData = viewModel.registerData.copy(username = newUsername)
                }
                , placeholder = { Text(text = "User Name") }
                , shape = MaterialTheme.shapes.small
            )
        }

        Column {
            Text(text = "Email Address"
                , style = MaterialTheme.typography.bodySmall
                , fontWeight = FontWeight.Bold
                , modifier = Modifier.padding(bottom = 5.dp)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.registerData.email,
                onValueChange = { newEmail: String ->
                    viewModel.registerData = viewModel.registerData.copy(email = newEmail)
                }
                , placeholder = { Text(text = "Email Address") }
                , shape = MaterialTheme.shapes.small
            )
        }

        Column{
            Text(text = "Password"
                , style = MaterialTheme.typography.bodySmall
                , fontWeight = FontWeight.Bold
                , modifier = Modifier.padding(bottom = 5.dp)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.registerData.password,
                onValueChange = { newPassword: String ->
                    viewModel.registerData = viewModel.registerData.copy(password = newPassword)
                }
                , placeholder = { Text(text = "Create a password") }
                , shape = MaterialTheme.shapes.small
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

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.registerData.confirmPassword,
                onValueChange = { newConfirmPassword: String ->
                    viewModel.registerData = viewModel.registerData.copy(confirmPassword = newConfirmPassword)
                },
                placeholder = { Text(text = "Confirm password") }
                , shape = MaterialTheme.shapes.small
                , trailingIcon = {
                    IconButton(onClick = {
                        viewModel.toggleConfirmPasswordVisibility()
                    }) {
                        Icon(painter = painterResource(id = viewModel.iconConfirmPassword()) , contentDescription = "Visibility")
                    }
                }
                , visualTransformation = if (viewModel.confirmPasswordVisible) {
                    VisualTransformation.None
                } else{
                    PasswordVisualTransformation()
                }
                , keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Button(onClick = {
                viewModel.register()
            }
                , modifier = Modifier.fillMaxWidth()
                , shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Register", modifier = Modifier.padding(5.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth().clickable {
                    viewModel.goToLogin(navHostController)
                },
                verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "Already have an account? ", fontSize = 13.sp)
                Text(text = "Login", color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)


            }
        }
    }
}