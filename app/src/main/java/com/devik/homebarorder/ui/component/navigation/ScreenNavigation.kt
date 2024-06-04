package com.devik.homebarorder.ui.component.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devik.homebarorder.ui.screen.menu.MenuScreen
import com.devik.homebarorder.ui.screen.signin.SignInScreen
import com.devik.homebarorder.ui.screen.signin.SignInViewModel

@Composable
fun ScreenNavigation() {
    val navController = rememberNavController()
    val viewModel: SignInViewModel = hiltViewModel()
    viewModel.checkUserInfo()

    val startDestination: String = if (!viewModel.isSignIn.value) {
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
    }
}

object NavigationRoute {
    const val SIGN_IN_SCREEN = "sign_in_screen"
    const val MENU_SCREEN = "menu_screen"
}