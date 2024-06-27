package com.devik.homebarorder.ui.component.topappbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.devik.homebarorder.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String ,
    onValueChange:(String) -> Unit,
    onBackClick:() -> Unit,
    onSearchClick:() -> Unit,
) {

    TopAppBar(
        title = {
            TextField(
                value = text,
                onValueChange = onValueChange ,
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent, focusedIndicatorColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_search_menu),
                        color = Color.Gray
                    )
                }
                )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(R.string.content_description_back_button))
            }
        },
        actions = {
            IconButton(onClick =  onSearchClick) {
                Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.content_description_search_button))
            }
        }
    )
}