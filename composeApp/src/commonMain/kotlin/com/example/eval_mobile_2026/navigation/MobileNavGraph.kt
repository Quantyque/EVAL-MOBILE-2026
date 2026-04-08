package com.example.eval_mobile_2026.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.eval_mobile_2026.presentation.locationdetail.LocationDetailScreen
import com.example.eval_mobile_2026.presentation.locationlist.LocationListScreen

@Composable
fun MobileNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.LocationList,
        modifier = modifier
    ) {
        composable<AppRoute.LocationList> {
            LocationListScreen(
                onLocationClick = { id ->
                    navController.navigate(AppRoute.LocationDetail(locationId = id))
                }
            )
        }

        composable<AppRoute.LocationDetail> { backStackEntry ->
            val route: AppRoute.LocationDetail = backStackEntry.toRoute()
            LocationDetailScreen(locationId = route.locationId)
        }
    }
}
