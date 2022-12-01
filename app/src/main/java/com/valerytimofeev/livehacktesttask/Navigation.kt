package com.valerytimofeev.livehacktesttask

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.valerytimofeev.livehacktesttask.ui.company_details_screen.CompanyDetailsScreen
import com.valerytimofeev.livehacktesttask.ui.company_list_screen.CompanyListScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "company_list"
    ) {
        composable("company_list") {
            CompanyListScreen(navController = navController)
        }
        composable(
            "company_list/{companyId}/{tileColor}",
            arguments = listOf(
                navArgument("companyId") {
                    type = NavType.IntType
                },
                navArgument("tileColor") {
                    type = NavType.LongType
                }
            )
        ) {
            val companyId = remember {
                it.arguments?.getInt("companyId")
            }
            val tileColor = remember {
                val color = it.arguments?.getLong("tileColor")
                color?.let { Color(it) } ?: Color.LightGray
            }
            CompanyDetailsScreen(
                navController = navController,
                companyId = companyId ?: -1,
                tileColor = tileColor
            )
        }

    }
}