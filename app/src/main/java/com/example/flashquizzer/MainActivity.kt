package com.example.flashquizzer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.navigation.FlashQuizzerNavigation
import com.example.flashquizzer.ui.theme.FlashQuizzerTheme
import com.example.flashquizzer.ui.bottomBar.TopBarView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlashQuizzerApp()
        }
    }
}

@Composable
fun FlashQuizzerApp() {
    var barIsVisible = remember { mutableStateOf(true) }
    val navController = rememberNavController()

    FlashQuizzerTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (barIsVisible.value) {
                    TopBarView(navController)
                }
            }
        ) { innerPadding ->
            FlashQuizzerNavigation(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            ) { barVisible ->
                barIsVisible.value = barVisible
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    FlashQuizzerTheme {
        FlashQuizzerApp()
    }
}