package com.devik.homebarorder.ui.component.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devik.homebarorder.data.source.local.database.PreferenceManager
import com.devik.homebarorder.ui.screen.managecategory.ManageCategoryScreen
import com.devik.homebarorder.ui.screen.managemenu.ManageMenuScreen
import com.devik.homebarorder.ui.screen.menu.TabletMenuScreen
import com.devik.homebarorder.ui.screen.menu.MobileMenuScreen
import com.devik.homebarorder.ui.screen.menueditorscreen.MenuEditorScreen
import com.devik.homebarorder.ui.screen.setting.SettingScreen
import com.devik.homebarorder.ui.screen.signin.SignInScreen
import com.devik.homebarorder.util.Constants
import com.devik.homebarorder.util.isMobile

@Composable
fun ScreenNavigation(navController: NavHostController) {
    val preferenceManager = PreferenceManager(LocalContext.current)
    val userMail = preferenceManager.getString(Constants.KEY_MAIL_ADDRESS, "")
    val userImage = preferenceManager.getString(Constants.KEY_PROFILE_IMAGE, "")

    val startDestination: String = if (userMail.isBlank() && userImage.isBlank()) {
        NavigationRoute.SIGN_IN_SCREEN
    } else {
        if (isMobile()) {
            NavigationRoute.MOBILE_MENU_SCREEN
        } else {
            NavigationRoute.TABLET_MENU_SCREEN
        }
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavigationRoute.SIGN_IN_SCREEN) {
            SignInScreen(navController = navController)
        }
        composable(NavigationRoute.MOBILE_MENU_SCREEN) {
            MobileMenuScreen(navController = navController)
        }
        composable(NavigationRoute.TABLET_MENU_SCREEN) {
            TabletMenuScreen(navController = navController)
        }
        composable(NavigationRoute.MANAGE_CATEGORY_SCREEN) {
            ManageCategoryScreen(navController = navController)
        }
        composable(NavigationRoute.MANAGE_MENU_SCREEN) {
            ManageMenuScreen(navController = navController)
        }
        composable(NavigationRoute.MENU_EDITOR_SCREEN) {
            MenuEditorScreen(navController = navController)
        }
        composable(NavigationRoute.SETTING_SCREEN) {
            SettingScreen(navController = navController)
        }
        composable(
            route = "${NavigationRoute.MENU_EDITOR_SCREEN}/{${Constants.NAVIGATION_ARGUMENT_KEY_EDIT_MENU}}",
            arguments = listOf(navArgument(Constants.NAVIGATION_ARGUMENT_KEY_EDIT_MENU) {
                type = NavType.IntType
            })
        ) {
            val menuUid = it.arguments?.getInt(Constants.NAVIGATION_ARGUMENT_KEY_EDIT_MENU)
            MenuEditorScreen(navController = navController, editTargetMenuUid = menuUid)
        }
    }
}

object NavigationRoute {
    const val SIGN_IN_SCREEN = "sign_in_screen"
    const val TABLET_MENU_SCREEN = "menu_screen"
    const val MANAGE_CATEGORY_SCREEN = "manage_category_screen"
    const val MANAGE_MENU_SCREEN = "manage_menu_screen"
    const val MENU_EDITOR_SCREEN = "menu_editor_screen"
    const val SETTING_SCREEN = "setting_screen"
    const val MOBILE_MENU_SCREEN = "mobile_menu_screen"
}