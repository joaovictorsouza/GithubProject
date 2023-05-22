package br.eng.joaovictor.ghproject.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.eng.joaovictor.ghproject.ui.home.HomeScreen
import br.eng.joaovictor.ghproject.ui.user.UserScreen

@Composable
fun GhNavGraph(
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = GhDestinations.HOME_ROUTE,
    navigateToUser: (user: String?) -> Unit
    ) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        composable(route = GhDestinations.HOME_ROUTE){
            HomeScreen(
                widthSizeClass = widthSizeClass,
                onSelectedItem = { user -> navigateToUser(user)},
                modifier = modifier)
        }
        composable(route = "${GhDestinations.USER_ROUTE}?login={login}",
            arguments = listOf(
                navArgument("login") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )){ backStackEntry ->
            UserScreen(
                modifier = modifier,
                widthSizeClass = widthSizeClass,
                userLogin = backStackEntry.arguments?.getString("login") ?: "")
        }
    }
}