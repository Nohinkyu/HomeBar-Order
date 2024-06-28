package com.devik.homebarorder.ui.component.topappbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.devik.homebarorder.R
import com.devik.homebarorder.ui.theme.OrangeSoda

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuIconWithTitleAppBar(title: String, menuIconOnClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title, fontSize = 24.sp, color = OrangeSoda)
        },
        navigationIcon = {
            IconButton(onClick = {menuIconOnClick()}) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.content_description_menu)
                )
            }
        },
    )
}