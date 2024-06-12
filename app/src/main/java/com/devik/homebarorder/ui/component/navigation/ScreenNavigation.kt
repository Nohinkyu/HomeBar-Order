package com.devik.homebarorder.ui.component.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devik.homebarorder.data.source.local.database.PreferenceManager
import com.devik.homebarorder.ui.screen.managecategory.ManageCategoryScreen
import com.devik.homebarorder.ui.screen.menu.MenuScreen
import com.devik.homebarorder.ui.screen.signin.SignInScreen
import com.devik.homebarorder.util.Constants

@Composable
fun ScreenNavigation(navController: NavHostController) {
    val preferenceManager = PreferenceManager(LocalContext.current)
    val userMail = preferenceManager.getString(Constants.KEY_MAIL_ADDRESS,"")
    val userImage = preferenceManager.getString(Constants.KEY_PROFILE_IMAGE,"")

    val startDestination: String = if (userMail.isBlank() && userImage.isBlank()) {
        NavigationRoute.SIGN_IN_SCREEN
    } else {
        NavigationRoute.MENU_SCREEN
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavigationRoute.SIGN_IN_SCREEN) {
            SignInScreen(navController = navController)
        }
        composable(NavigationRoute.MENU_SCREEN) {
            MenuScreen(navController = navController)
        }
        composable(NavigationRoute.MANAGE_CATEGORY_SCREEN) {
            ManageCategoryScreen(navController = navController)
        }
    }
}

object NavigationRoute {
    const val SIGN_IN_SCREEN = "sign_in_screen"
    const val MENU_SCREEN = "menu_screen"
    const val MANAGE_CATEGORY_SCREEN = "manage_category_screen"
}