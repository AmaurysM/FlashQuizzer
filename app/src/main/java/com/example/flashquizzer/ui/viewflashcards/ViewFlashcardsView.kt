package com.example.flashquizzer.ui.viewflashcards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.model.Flashcard
import com.example.flashquizzer.viewmodel.ViewFlashcardsViewModel

@Composable
fun ViewFlashcardsView(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ViewFlashcardsViewModel = viewModel()
) {
    val flashcards by viewModel.flashcards.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        flashcards.forEach { flashcard -> // Iterate over the list of flashcards.
            var isFrontSide by remember { mutableStateOf(true) } // State variable to track the side of the flashcard

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        isFrontSide = !isFrontSide
                    },
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Box(
                    modifier = Modifier
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isFrontSide) flashcard.question else flashcard.answer, // Display the question or answer based on the side of the flashcard
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
