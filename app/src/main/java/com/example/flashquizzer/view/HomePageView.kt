package com.example.flashquizzer.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.model.AuthManager
import com.example.flashquizzer.model.AuthState
import com.example.flashquizzer.viewmodel.HomePageViewmodel

@Preview(showBackground = true)
@Composable
fun HomePageView(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: HomePageViewmodel = viewModel()
) {
    val authState = AuthManager.authState.observeAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "FlashQuizzer",
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            modifier = modifier.padding(bottom = 20.dp),
            fontWeight = FontWeight.Bold
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { viewModel.goUploadDoc(navController) },
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Text(text = "Upload Document")
            }

            Button(
                onClick = { viewModel.goCreateFlashcards(navController) },
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Text(text = "Create Flashcards")

            }
            Button(onClick = { viewModel.goTakeQuiz(navController) },
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Text(text = "Take Quiz")

            }
        }

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "user icon",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        if (authState.value == AuthState.Unauthenticated) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.goLogin(navController) },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = "Login")
                }

                Text(text = "or", modifier = Modifier.padding(horizontal = 8.dp))

                Button(
                    onClick = { viewModel.goRegister(navController) },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = "Register")
                }
            }
        } else {
            Button(onClick = { viewModel.logout() }) {
                Text(text = "Sign out")
            }
        }
    }
}