package com.example.flashquizzer.ui.folder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.flashquizzer.model.FolderDC

@Composable
fun Folder(folder: FolderDC, navController: NavHostController) { // This is the composable function
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                // Navigate to the folder's content view
                navController.navigate("folder/${folder.id}")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = folder.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Text(
                text = "See more",
                modifier = Modifier.padding(horizontal = 10.dp),
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
        }

        if (folder.decks == null) { // If the folder has no decks
            Button(
                onClick = { /* do nothing */ },
                shape = RoundedCornerShape(15.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "", modifier = Modifier.size(150.dp))
            }
        } else {
            Row {
                folder.decks?.forEach { deck ->
                    Box {
                        Text(text = deck.name)
                    }
                }
            }
        }
    }
}
