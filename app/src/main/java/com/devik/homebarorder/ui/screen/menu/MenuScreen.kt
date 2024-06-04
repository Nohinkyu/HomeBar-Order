package com.devik.homebarorder.ui.screen.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devik.homebarorder.R
import com.devik.homebarorder.ui.component.topappbar.MenuIconWithTitleAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {

    val context = LocalContext.current
    val viewModel: MenuScreenViewModel = hiltViewModel()

    Scaffold(
        topBar = { MenuIconWithTitleAppBar(context.getString(R.string.app_name)) },
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