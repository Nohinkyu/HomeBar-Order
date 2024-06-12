package com.devik.homebarorder.ui.component.drawermenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devik.homebarorder.R
import com.devik.homebarorder.data.source.local.database.PreferenceManager
import com.devik.homebarorder.ui.component.navigation.NavigationRoute
import com.devik.homebarorder.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerNaviMenu(
    drawerState: DrawerState,
    navController: NavController,
    scope: CoroutineScope,
    content: @Composable () -> Unit
) {

    val drawerMenuItems = listOf<ScreenInfo>(
        ScreenInfo(
            stringResource(R.string.drawer_menu_screen_name_menu),
            NavigationRoute.MENU_SCREEN
        ),
        ScreenInfo(
            stringResource(R.string.drawer_menu_screen_name_manage_category),
            NavigationRoute.MANAGE_CATEGORY_SCREEN
        )
    )
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    val gesturesEnabled = drawerState.currentValue == DrawerValue.Open

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val currentRoute = backStackEntry.destination.route
            selectedItem = drawerMenuItems.indexOfFirst { it.screenRoute == currentRoute }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.size(16.dp))
                DrawerMenuHeader()
                Spacer(modifier = Modifier.size(16.dp))
                drawerMenuItems.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.screenName) },
                        selected = index == selectedItem,
                        onClick = {
                            if (selectedItem != index) {
                                selectedItem = index
                                scope.launch {
                                    drawerState.close()
                                    navController.navigate(item.screenRoute)
                                }
                            } else {
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        })
                }
            }
        },
        content = content
    )
}

@Composable
private fun DrawerMenuHeader() {
    val context = LocalContext.current
    val preferenceManager = PreferenceManager(context)
    val userMailAddressState by remember {
        mutableStateOf(preferenceManager.getString(Constants.KEY_MAIL_ADDRESS, ""))
    }
    val userImageState by remember {
        mutableStateOf(preferenceManager.getString(Constants.KEY_PROFILE_IMAGE, ""))
    }

    Row() {
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        ) {
            AsyncImage(
                model = userImageState.substring(1, userImageState.length - 1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(text = userMailAddressState, modifier = Modifier.padding(8.dp))
    }
}

data class ScreenInfo(
    val screenName: String,
    val screenRoute: String,
)