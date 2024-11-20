package com.example.flashquizzer.ui.bottomBar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar{
        bottomBarDestinations.forEachIndexed { index,screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp)
                        , painter = painterResource(screen.icon)
                        , contentDescription = screen.title + " Icon"
                        , tint = if (selectedItem == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                , label = { Text(text = screen.title) }
                , selected = false
                , onClick = {
                    selectedItem = index
                    viewModel.navigateToDestination(navController, screen)
                }
            )
        }
    }
}