package br.eng.joaovictor.ghproject.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object GhDestinations{
    const val HOME_ROUTE = "home"
    const val USER_ROUTE = "user"
}

class GhNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(GhDestinations.HOME_ROUTE) {
            launchSingleTop = true
            restoreState = true
        }
    }

     val navigateToUser: (user: String?) -> Unit = {
        navController.navigate(GhDestinations.USER_ROUTE)
    }
}