package com.example.flashquizzer.ui.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.R
import com.example.flashquizzer.model.FolderDC
import com.example.flashquizzer.ui.folder.Folder // This is the composable function
@Composable
fun HomePageView( // This is the composable function
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: HomePageViewmodel = viewModel() // This is the view model
) {
    val userFolders by viewModel.userFolders.collectAsState() // Collect the user folders from the view model
    val folderCreation by viewModel.folderCreation
    val newFolderName by viewModel.newFolderName
    val query by viewModel.query // Collect the query from the view model.

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.query.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp),
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.onSearch()
                    }
                ),
                shape = MaterialTheme.shapes.extraLarge
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "My Flashcards",
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Your Personal Study Hub",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                Button(
                    onClick = {
                        viewModel.goCreateFolder() // Navigate to the folder creation screen
                    }
                ) {
                    if (!viewModel.hasFolders()) { // If the user has no folders nothing happens yet
                        // nothing
                    } else {
                        Text(text = "Create a folder")
                    }
                }

                if (!viewModel.hasFolders()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "It seems you don't have any folders yet.")
                        Button(
                            onClick = {
                                viewModel.goCreateFolder()
                            }
                        ) {
                            Text(text = "Create a folder")
                        }
                    }

                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(userFolders) { folder ->
                            // Pass the folder to the Folder composable
                            Folder(folder, navController)
                        }
                    }
                }
            }
        }

        if (folderCreation) { // If the user is creating a folder
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                    .clickable(enabled = false) { /* Do  */ }
                    .padding(horizontal = 15.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Create a new folder")
                    OutlinedTextField(
                        value = newFolderName,
                        onValueChange = { viewModel.newFolderName.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Folder Name") }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.dontCreateFolder() },
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }
                        Button(
                            onClick = { viewModel.createNewFolder() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Create", modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}
