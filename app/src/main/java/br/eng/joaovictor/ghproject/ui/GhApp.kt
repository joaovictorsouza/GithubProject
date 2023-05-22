@file:OptIn(ExperimentalMaterial3Api::class)

package br.eng.joaovictor.ghproject.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.eng.joaovictor.ghproject.R
import br.eng.joaovictor.ghproject.ui.home.HomeScreen
import br.eng.joaovictor.ghproject.ui.theme.GithubProjectTheme

@Composable
fun GhApp(
    widthSizeClass: WindowWidthSizeClass,
) {
    val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
    GithubProjectTheme {
        val navController = rememberNavController()
        val backStackEntry = navController.currentBackStackEntryAsState()
        val isSearching = remember {
            mutableStateOf(false)
        }
        var text by rememberSaveable { mutableStateOf("") }
        var active by rememberSaveable { mutableStateOf(false) }

        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    if(!isExpandedScreen) {
                        TopAppBar(
                            title = { Text(stringResource(id = R.string.app_name)) },
                            navigationIcon = {
                                if (backStackEntry.value?.destination?.route != GhDestinations.HOME_ROUTE)
                                {
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            ) {
                Row {
                    if (isExpandedScreen) {
                        NavigationRail {
                            NavigationRailItem(
                                icon = { Icon(Icons.Filled.Home, contentDescription = "") },
                                selected = backStackEntry.value?.destination?.route == GhDestinations.HOME_ROUTE,
                                onClick = {
                                    if (backStackEntry.value?.destination?.route != GhDestinations.HOME_ROUTE)
                                        navController.popBackStack()
                                }
                            )
                        }
                    }
                    Column {
                        GhNavGraph(
                            modifier = Modifier.padding(it),
                            navController = navController,
                            widthSizeClass = widthSizeClass,
                            navigateToUser = { user -> navController.navigate("${GhDestinations.USER_ROUTE}?login=${user}") }
                        )
                    }
                }
            }
        }
    }
}