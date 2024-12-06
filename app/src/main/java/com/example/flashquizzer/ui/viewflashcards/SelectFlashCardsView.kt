package com.example.flashquizzer.ui.flashcards

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.navigation.FlashQuizzerDestinations
import com.example.flashquizzer.viewmodel.UploadDocViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class) // Required for the DropdownMenu API
@Composable
fun SelectFlashcardsView(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as ComponentActivity // Get the current activity
    val viewModel: UploadDocViewModel = viewModel(activity) // Create an instance of the view model
    val unreviewedFlashcards by viewModel.unreviewedFlashcards.collectAsState()
    val selectedFlashcards by viewModel.selectedFlashcards.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val folders by viewModel.folders.collectAsState()
    val scope = rememberCoroutineScope()

    // State variables for manual flashcard creation.
    var newQuestion by remember { mutableStateOf("") }
    var newAnswer by remember { mutableStateOf("") }

    // State for folder selection
    var expanded by remember { mutableStateOf(false) }
    var selectedFolderName by remember { mutableStateOf("Select Folder") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Review Flashcards") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Folder selection dropdown
            if (folders.isNotEmpty()) {
                Box {
                    Button(onClick = { expanded = true }) {
                        Text(selectedFolderName)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        folders.forEach { folder ->
                            DropdownMenuItem(
                                text = { Text(folder.name) },
                                onClick = {
                                    selectedFolderName = folder.name
                                    viewModel.selectedFolderId = folder.id
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            } else {
                Text("No folders available. Please create a folder first.")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                // Display a loading indicator
                CircularProgressIndicator()
            } else if (unreviewedFlashcards.isEmpty()) {
                Text("No flashcards to review. You can create your own:")
                OutlinedTextField(
                    value = newQuestion,
                    onValueChange = { newQuestion = it },
                    label = { Text("Question") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = newAnswer,
                    onValueChange = { newAnswer = it },
                    label = { Text("Answer") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        val customFlashcard = "Question: $newQuestion\nAnswer: $newAnswer"
                        viewModel.addCustomFlashcard(customFlashcard)
                        newQuestion = ""
                        newAnswer = ""
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Add Flashcard")
                }
            } else {
                // Display the list of unreviewed flashcards
                Text("Review flashcards:")
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    itemsIndexed(unreviewedFlashcards) { index, flashcard ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = flashcard,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    IconButton(
                                        onClick = {
                                            viewModel.rejectFlashcard(flashcard)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Reject",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            viewModel.confirmFlashcard(flashcard)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Confirm",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (error != null) {
                Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Confirm button
            Button(
                onClick = {
                    scope.launch {
                        viewModel.saveFlashcardsToFirebase()
                        navController.navigate(FlashQuizzerDestinations.Home.route) {
                            popUpTo(FlashQuizzerDestinations.Home.route) {
                                inclusive = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.End),
                enabled = !isLoading && viewModel.selectedFolderId != null && selectedFlashcards.isNotEmpty()
            ) {
                Icon(Icons.Default.Check, contentDescription = "Confirm")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Confirm")
            }
        }
    }
}
