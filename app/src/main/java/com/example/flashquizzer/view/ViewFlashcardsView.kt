package com.example.flashquizzer.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flashquizzer.viewmodel.ViewFlashcardsViewmodel


@Composable
fun ViewFlashcardsView(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: ViewFlashcardsViewmodel = viewModel()
) {

}