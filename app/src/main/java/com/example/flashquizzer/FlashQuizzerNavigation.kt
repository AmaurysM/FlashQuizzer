package com.example.flashquizzer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.view.HomePageView
import com.example.flashquizzer.view.TakeQuizView
import com.example.flashquizzer.view.UploadDocView
import com.example.flashquizzer.view.ViewFlashcardsView


@Composable
fun FlashQuizzerNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){

    NavHost(
        navController = navController,
        startDestination = FlashQuizzerDestinations.Home.route,
        modifier = modifier
    ){
        composable(FlashQuizzerDestinations.Home.route){
            HomePageView(navController, modifier)
        }
        composable(FlashQuizzerDestinations.UploadDoc.route){
            UploadDocView(navController, modifier)
        }
        composable(FlashQuizzerDestinations.ViewFlashcards.route){
            ViewFlashcardsView(navController, modifier)
        }
        composable(FlashQuizzerDestinations.TakeQuiz.route){
            TakeQuizView(navController, modifier)
        }
    }
}