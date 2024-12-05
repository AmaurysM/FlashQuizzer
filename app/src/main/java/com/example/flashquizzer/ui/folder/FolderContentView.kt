package com.example.flashquizzer.ui.folder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.model.Card
import com.example.flashquizzer.viewmodel.FolderContentViewModel

@Composable
fun FolderContentView(
    navController: NavHostController,
    folderId: String,
    modifier: Modifier = Modifier,
    viewModel: FolderContentViewModel = viewModel()
) {
    val flashcards by viewModel.flashcards.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFlashcards(folderId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Flashcards",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            // Show a loading indicator while flashcards are being loaded
            CircularProgressIndicator()
        } else if (flashcards.isEmpty()) {
            Text("No flashcards available in this folder.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(flashcards) { flashcard ->
                    FlashcardItem(flashcard)
                }
            }
        }
    }
}

@Composable
fun FlashcardItem(flashcard: Card) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Question: ${flashcard.question}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Answer: ${flashcard.answer}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
