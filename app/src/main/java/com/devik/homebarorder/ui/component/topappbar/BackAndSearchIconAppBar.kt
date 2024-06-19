package com.devik.homebarorder.ui.component.topappbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackAndSearchIconAppBar(title: String, backIconClick: () -> Unit, searchIconClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, fontSize = 24.sp) },
        navigationIcon = {
            IconButton(onClick = backIconClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = searchIconClick) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            }
        }
    )
}