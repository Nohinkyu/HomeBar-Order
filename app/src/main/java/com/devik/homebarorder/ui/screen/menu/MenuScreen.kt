package com.devik.homebarorder.ui.screen.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devik.homebarorder.R
import com.devik.homebarorder.ui.component.drawermenu.DrawerNaviMenu
import com.devik.homebarorder.ui.component.topappbar.MenuIconWithTitleAppBar
import com.devik.homebarorder.ui.dialog.ManualDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {

    CompositionLocalProvider(
        androidx.lifecycle.compose.LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current,
    ) {
        val viewModel: MenuScreenViewModel = hiltViewModel()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val manualDialogState by viewModel.manualDialogState.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()
        viewModel.checkIsFirstSignIn()

        if (manualDialogState) {
            ManualDialog() {
                viewModel.manualDialogDismissRequest()
            }
        }

        DrawerNaviMenu(
            drawerState = drawerState,
            navController = navController,
            scope = scope){

            Scaffold(
                topBar = {
                    MenuIconWithTitleAppBar(menuIconOnClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }, title = stringResource(id = R.string.app_name))
                },
                modifier = Modifier.padding(top = 8.dp)
            ) { paddingValues ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {

                }
            }
        }
    }
}