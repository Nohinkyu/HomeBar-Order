package com.devik.homebarorder.ui.screen.managecategory

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devik.homebarorder.R
import com.devik.homebarorder.data.source.local.database.CategoryEntity
import com.devik.homebarorder.ui.component.topappbar.BackIconWithTitleAppBar
import com.devik.homebarorder.ui.dialog.YesOrNoDialog
import com.devik.homebarorder.ui.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCategoryScreen() {

    CompositionLocalProvider(
        androidx.lifecycle.compose.LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current,
    ) {
        val context = LocalContext.current
        val viewModel: ManageCategoryViewModel = hiltViewModel()
        val categoryList by viewModel.categoryList.collectAsStateWithLifecycle()
        val categoryTextState by viewModel.categoryTextState.collectAsStateWithLifecycle()
        val deleteDialogState by viewModel.deleteDialogState.collectAsStateWithLifecycle()
        val deleteTargetCategory by viewModel.deleteTargetCategory.collectAsStateWithLifecycle()
        viewModel.getAllCategoryList()

        Scaffold(
            topBar = { BackIconWithTitleAppBar(title = stringResource(R.string.top_appbar_title_category)) },
            modifier = Modifier.padding(8.dp)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Divider(thickness = 1.dp, color = LightGray)

                Spacer(modifier = Modifier.size(40.dp))

                OutlinedTextField(
                    value = categoryTextState,
                    onValueChange = { viewModel.onCategoryTextStateChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(start = 24.dp, end = 24.dp),
                    shape = RoundedCornerShape(50.dp),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.placeholder_add_category),
                            color = Gray
                        )
                    },
                    maxLines = 1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Black,
                        focusedBorderColor = Color.Black
                    ),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 20.sp),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (categoryTextState.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.message_is_category_blank),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    viewModel.insertCategory(
                                        CategoryEntity(
                                            category = categoryTextState,
                                        )
                                    )
                                }
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.content_description_add_category)
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = stringResource(R.string.mange_category_manual).trimMargin(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp),
                    fontSize = 16.sp,
                    color = Gray
                )
                Spacer(modifier = Modifier.size(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp)
                ) {
                    items(categoryList) { item ->
                        ItemCategory(
                            categoryEntity = item,
                            onDeleteClick = {
                                viewModel.showDeleteDialog()
                                viewModel.setDeleteTargetCategory(item)
                            }
                        )
                    }
                }
            }
        }
        if (deleteDialogState) {
            YesOrNoDialog(
                body = stringResource(R.string.dialog_message_category_delete_body).trimMargin(),
                yesButtonText = stringResource(R.string.dialog_button_delete),
                onDismissRequest = { viewModel.closeDeleteDialog() },
                onYesClickRequest = {
                    with(viewModel) {
                        deleteCategory(deleteTargetCategory)
                        closeDeleteDialog()
                    }
                })
        }
    }
}

@Composable
private fun ItemCategory(categoryEntity: CategoryEntity, onDeleteClick: () -> Unit) {
    Spacer(modifier = Modifier.size(4.dp))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Text(
                text = categoryEntity.category,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
                fontSize = 24.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .height(80.dp)
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 8.dp)) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.content_description_edit_category)
                    )
                }
                IconButton(onClick = onDeleteClick, modifier = Modifier.padding(end = 8.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.content_description_delete_category)
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.size(4.dp))
}