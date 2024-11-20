package com.example.flashquizzer.ui.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.R

@Preview(showBackground = true)
@Composable
fun HomePageView(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: HomePageViewmodel = viewModel(),
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            OutlinedTextField(
                viewModel.query.value,
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
                    text = "Your Personal Study Hub"
                    , fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    , color = MaterialTheme.colorScheme.primaryContainer
                )

                if (!viewModel.hasFolders()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                        , verticalArrangement = Arrangement.Center
                        , horizontalAlignment = Alignment.CenterHorizontally
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
                    Column {
                        viewModel.userFolders.forEach { folder ->
                            Folder(folder)
                        }
                    }
                }
            }
        }

        if (viewModel.folderCreation.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                    .clickable(enabled = false) { ;/* Do Nothing */}
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                , verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .padding(16.dp)
                    , horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Create a new folder")
                    OutlinedTextField(
                        value = viewModel.newFolderName.value,
                        onValueChange = { viewModel.newFolderName.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Folder Name") }
                    )
                    Row(modifier = Modifier.fillMaxWidth()
                        , horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = { viewModel.dontCreateFolder() }
                            , modifier.fillMaxWidth(0.3f)
                                .padding(vertical = 8.dp)
                            , shape = RoundedCornerShape(8.dp)
                        ){
                            Icon(ImageVector.vectorResource(
                                id = R.drawable.baseline_close_24)
                                , contentDescription = "Close"
                                , modifier = Modifier.padding(vertical = 6.dp))
                        }
                        Button(onClick = { viewModel.createNewFolder() }
                            , modifier.fillMaxWidth()
                                .padding(vertical = 8.dp)
                            , shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Create", modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }

                }
            }
        }
    }
}