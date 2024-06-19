package com.devik.homebarorder.ui.screen.menueditorscreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devik.homebarorder.R
import com.devik.homebarorder.data.source.local.database.MenuEntity
import com.devik.homebarorder.ui.component.topappbar.BackIconWithTitleAppBar
import com.devik.homebarorder.ui.theme.LightGray
import com.devik.homebarorder.ui.theme.MediumGray
import com.devik.homebarorder.ui.theme.OrangeSoda

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuEditorScreen(navController: NavController, editTargetMenuUid: Int? = null) {

    CompositionLocalProvider(
        androidx.lifecycle.compose.LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current,
    ) {
        val viewModel: MenuEditorViewModel = hiltViewModel()
        val context = LocalContext.current

        val menuCategory by viewModel.menuCategory.collectAsStateWithLifecycle()
        val categoryList by viewModel.categoryList.collectAsStateWithLifecycle()
        val menuName by viewModel.menuName.collectAsStateWithLifecycle()
        val menuInfo by viewModel.menuInfo.collectAsStateWithLifecycle()
        val menuPrice by viewModel.menuPrice.collectAsStateWithLifecycle()
        val menuImageBitmap by viewModel.menuImageBitmap.collectAsStateWithLifecycle()
        val isMenuNameCategoryBlank by viewModel.isMenuNameCategoryBlank.collectAsStateWithLifecycle()
        val buttonTextState by viewModel.buttonTextState.collectAsStateWithLifecycle()
        var expandStatus by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            viewModel.getCategoryList()
            viewModel.setButtonText(editTargetMenuUid)
            if (editTargetMenuUid != null) {
                viewModel.getEditTargetMenu(editTargetMenuUid)
            }
        }

        val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                if (uri != null) {
                    viewModel.setMenuImageBitmap(setImageBitmap(context, uri))
                }
            }
        )

        if (isMenuNameCategoryBlank) {
            Toast.makeText(context, stringResource(R.string.message_is_category_and_menu_name_blank), Toast.LENGTH_SHORT).show()
        }

        Scaffold(
            topBar = {
                BackIconWithTitleAppBar(title = stringResource(R.string.top_appbar_title_edit_add_menu),
                    onBackButtonClick = { navController.popBackStack() })
            },
            modifier = Modifier.padding(top = 8.dp)
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Divider(thickness = 1.dp, color = LightGray)
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .fillMaxHeight(0.25f)
                                .padding(start = 24.dp, top = 24.dp)
                                .background(color = MediumGray, shape = RoundedCornerShape(10.dp))
                                .clickable {
                                    singlePhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                },
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_camera),
                                modifier = Modifier
                                    .fillMaxSize(0.5f)
                                    .align(Alignment.Center),
                                contentDescription = stringResource(R.string.content_description_get_menu_image)
                            )
                            AsyncImage(
                                model = menuImageBitmap,
                                contentDescription = stringResource(R.string.content_description_menu_image),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop,
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 16.dp)
                        ) {
                            OutlinedTextField(
                                value = menuName,
                                maxLines = 1,
                                onValueChange = { viewModel.setMenuName(it) },
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.placeholder_menu_name),
                                        color = Gray
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Black
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                            )

                            OutlinedTextField(
                                value = menuPrice,
                                maxLines = 1,
                                onValueChange = { value ->
                                    if (value.all { it.isDigit() }) {
                                        viewModel.setMenuPrice(value)
                                    }
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.placeholder_menu_price),
                                        color = Gray
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Black
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                            )

                            OutlinedTextField(
                                value = menuInfo,
                                maxLines = 4,
                                onValueChange = { viewModel.setMenuInfo(it) },
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.placeholder_menu_info),
                                        color = Gray
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Black
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                            )

                            Button(
                                onClick = { viewModel.deleteImageBitmap() },
                                shape = RoundedCornerShape(5.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .heightIn(56.dp)
                                    .padding(top = 16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 4.dp
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.button_delete_image),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Button(
                                onClick = { expandStatus = true },
                                shape = RoundedCornerShape(5.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .heightIn(56.dp)
                                    .padding(top = 16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 4.dp
                                )
                            ) {
                                Text(
                                    text = menuCategory.category,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            DropdownMenu(
                                expanded = expandStatus,
                                onDismissRequest = { expandStatus = false },
                                modifier = Modifier.background(Color.White)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(width = 100.dp, height = 200.dp)
                                        .background(
                                            Color.White
                                        )
                                ) {
                                    LazyColumn(modifier = Modifier.background(Color.White)) {
                                        items(categoryList) { item ->
                                            DropdownMenuItem(
                                                text = { Text(text = item.category) },
                                                onClick = {
                                                    viewModel.setCategory(item)
                                                    expandStatus = false
                                                })
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        if (editTargetMenuUid != null) {
                            viewModel.updateMenu()
                        } else {
                            viewModel.insertMenu()
                        }
                        navController.navigateUp()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(80.dp)
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeSoda)
                ) {
                    Text(text = buttonTextState, fontSize = 24.sp)
                }
            }
        }
    }
}

private fun setImageBitmap(context: Context, uri: Uri): Bitmap {
    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(context.contentResolver, uri)
        )
    } else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }
    return bitmap
}