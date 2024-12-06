package com.example.flashquizzer.ui.folder

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.model.Card
import com.example.flashquizzer.viewmodel.FolderContentViewModel

@Composable
fun FolderContentView( // This is the composable function
    navController: NavHostController,
    folderId: String,
    modifier: Modifier = Modifier,
    viewModel: FolderContentViewModel = viewModel() // This is the view model
) {
    // Collect the flashcards and loading state from the view model
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
            // Show a loading indicator while flashcards are being loaded.
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
// The FlashcardItem composable function is defined here
@Preview(showBackground = true)
@Composable
fun FlashcardItem(flashcard: Card = Card("Question", "Answer")) {
    var isFlipped by remember { mutableStateOf(false) }

    val rotateY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
        , label = ""
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    rotationY = rotateY
                    cameraDistance = 12f * density
                }
                .clickable { isFlipped = !isFlipped },
            shape = MaterialTheme.shapes.medium
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (rotateY <= 90f) {
                    Text(
                        text = "Question: ${flashcard.question}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "Answer: ${flashcard.answer}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.graphicsLayer {
                            rotationY = 180f
                        }
                    )
                }
            }
        }
    }
}
