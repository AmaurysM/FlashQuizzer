package com.example.flashquizzer.ui.uploaddoc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun UploadDocView(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: UploadDocViewmodel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
        , horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.Center
    ){
        Button(onClick = { viewModel.UploadDoc() }) {
            Text(text = "Upload Document")
        }
        Icon( imageVector = Icons.Rounded.Search
            , contentDescription = "user icon"
            , modifier = Modifier.size(100.dp))
    }
}