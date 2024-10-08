package com.devik.homebarorder.ui.component.topappbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.devik.homebarorder.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackIconWithTitleAppBar(title: String, onBackIconClick:() -> Unit) {
    TopAppBar(
        title = {
            Text(text = title, fontSize = 24.sp)
        },
        navigationIcon = {
            IconButton(onClick = onBackIconClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.content_description_back_button)
                )
            }
        }
    )
}