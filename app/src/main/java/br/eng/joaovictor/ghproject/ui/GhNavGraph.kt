package br.eng.joaovictor.ghproject.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.eng.joaovictor.ghproject.ui.home.HomeScreen

@Composable
fun GhNavGraph(
//    appContainer: AppContainer,
//    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = GhDestinations.HOME_ROUTE,

    ) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        composable(route = "home"){ HomeScreen() }
    }
}