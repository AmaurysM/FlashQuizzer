package com.example.flashquizzer.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.model.AuthManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun SplashView(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = viewModel()
) {
    val authState = AuthManager.authState.observeAsState()
    val contextForToast = LocalContext.current.applicationContext

    LaunchedEffect(Unit) {
        launch(Dispatchers.Main) {
            viewModel.waitAndNavigate(navHostController, authState.value!!)
        }
    }

    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Flash Quizzer"
            , style = MaterialTheme.typography.headlineLarge
            , color = MaterialTheme.colorScheme.background
            , fontWeight = FontWeight.Bold)
    }

}