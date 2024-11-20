package com.example.flashquizzer.ui.bottomBar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flashquizzer.navigation.bottomBarDestinations

@Preview(showBackground = true)
@Composable
fun TopBarView(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: BottomBarViewmodel = viewModel()
) {
    NavigationBar{
        bottomBarDestinations.forEach{ screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.icon)
                        , contentDescription = screen.title + " Icon"
                    )
                }
                , label = { Text(text = screen.title) }
                , selected = false
                , onClick = {
                    viewModel.navigateToDestination(navController, screen)
                }
            )
        }
    }
}