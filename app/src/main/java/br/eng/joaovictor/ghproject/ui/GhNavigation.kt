package br.eng.joaovictor.ghproject.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object GhDestinations{
    const val HOME_ROUTE = "home"
}

class GhNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(GhDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}