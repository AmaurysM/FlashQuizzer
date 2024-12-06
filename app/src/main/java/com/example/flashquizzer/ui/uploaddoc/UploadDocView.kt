package com.example.flashquizzer.ui.uploaddoc

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.viewmodel.UploadDocViewModel
import com.example.flashquizzer.navigation.FlashQuizzerDestinations

@Composable
fun UploadDocView(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as ComponentActivity
    val viewModel: UploadDocViewModel = viewModel(activity)
    val context = LocalContext.current

    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val unreviewedFlashcards by viewModel.unreviewedFlashcards.collectAsState()

    val documentUri = remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult( // Use the ActivityResultContracts.OpenDocument contract to open a document
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        documentUri.value = uri
        uri?.let {
            viewModel.extractTextFromFile(context, it)
        }
    }

    // Flag to prevent multiple navigations
    var hasNavigated by remember { mutableStateOf(false) }

    // Use LaunchedEffect to navigate when flashcards are generated
    LaunchedEffect(unreviewedFlashcards) {
        if (unreviewedFlashcards.isNotEmpty() && !hasNavigated) {
            hasNavigated = true
            navController.navigate(FlashQuizzerDestinations.SelectFlashcards.route)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Upload a Document",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                launcher.launch(
                    arrayOf(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                        "application/vnd.openxmlformats-officedocument.presentationml.presentation"
                    )
                )
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Choose Document")
        }

        if (isLoading) {
            CircularProgressIndicator()
        }

        if (error != null) {
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
