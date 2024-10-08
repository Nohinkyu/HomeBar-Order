package com.devik.homebarorder.ui.screen.menueditorscreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devik.homebarorder.R
import com.devik.homebarorder.extension.throttledClickable
import com.devik.homebarorder.ui.component.topappbar.BackIconWithTitleAppBar
import com.devik.homebarorder.ui.dialog.InProgressDialog
import com.devik.homebarorder.ui.dialog.YesOrNoDialog
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
        val coroutineScope = rememberCoroutineScope()

        val menuCategory by viewModel.menuCategory.collectAsStateWithLifecycle()
        val categoryList by viewModel.categoryList.collectAsStateWithLifecycle()
        val menuName by viewModel.menuName.collectAsStateWithLifecycle()
        val menuInfo by viewModel.menuInfo.collectAsStateWithLifecycle()
        val menuPrice by viewModel.menuPrice.collectAsStateWithLifecycle()
        val menuImageBitmap by viewModel.menuImageBitmap.collectAsStateWithLifecycle()
        val isNavigateDialogState by viewModel.isNavigateDialogState.collectAsStateWithLifecycle()
        val buttonTextState by viewModel.buttonTextState.collectAsStateWithLifecycle()
        val isMenuSaveSuccess by viewModel.isMenuSaveSuccess.collectAsStateWithLifecycle()
        val isInProgressDialogState by viewModel.isInProgressDialogState.collectAsStateWithLifecycle()
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
                    val imageFileSize = getImageFileSize(context, uri)
                    if(imageFileSize > 20.0) {
                        Toast.makeText(context, context.getString(R.string.toast_message_image_size_big), Toast.LENGTH_SHORT).show()
                    }else {
                        viewModel.setMenuImageBitmap(setImageBitmap(context, uri, imageFileSize))
                    }
                }
            }
        )

        if (isNavigateDialogState) {
            YesOrNoDialog(
                body = stringResource(R.string.navigate_dialog_body).trimMargin(),
                yesButtonText = stringResource(R.string.navigate_dialog_button_yes),
                onDismissRequest = { viewModel.closeNavigateUpDialog() },
                onYesClickRequest = { navController.navigateUp() }
            )
        }
        
        if(isInProgressDialogState){
            InProgressDialog(message = stringResource(R.string.menu_save_in_progress_dialog))
        }

        if(isMenuSaveSuccess){
            LaunchedEffect(Unit){
                navController.navigateUp()
            }
        }

        Scaffold(
            topBar = {
                BackIconWithTitleAppBar(
                    title = stringResource(R.string.top_appbar_title_edit_add_menu),
                    onBackIconClick = {
                        if (menuName.isNotBlank() ||
                            menuInfo.isNotBlank() ||
                            menuPrice.isNotBlank() ||
                            menuImageBitmap != null
                        ) {
                            viewModel.openNavigateUpDialog()
                        } else {
                            navController.navigateUp()
                        }
                    }
                )
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
                                .width(150.dp)
                                .height(200.dp)
                                .padding(start = 24.dp, top = 32.dp)
                                .background(color = MediumGray, shape = RoundedCornerShape(10.dp))
                                .throttledClickable(
                                    coroutineScope = coroutineScope,
                                    onClick = {
                                        singlePhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    }
                                ),
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

                            MenuEditTextFiled(
                                textValue = menuName,
                                onValueChange = { viewModel.setMenuName(it) },
                                maxLines = 1,
                                placeholder = stringResource(R.string.placeholder_menu_name)
                            )

                            MenuEditTextFiled(
                                textValue = menuPrice,
                                onValueChange = { value ->
                                    if (value.all { it.isDigit() }) {
                                        viewModel.setMenuPrice(value)
                                    }
                                },
                                maxLines = 1,
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                placeholder = stringResource(R.string.placeholder_menu_price)
                            )

                            MenuEditTextFiled(
                                textValue = menuInfo,
                                onValueChange = { viewModel.setMenuInfo(it) },
                                maxLines = 6,
                                placeholder = stringResource(R.string.placeholder_menu_info),
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
                        if (viewModel.checkMenuNameCategoryBlank()) {
                            if (editTargetMenuUid != null) {
                                viewModel.updateMenu()
                            } else {
                                viewModel.insertMenu()
                            }
//                            navController.navigateUp()
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.message_is_category_and_menu_name_blank),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuEditTextFiled(
    textValue: String,
    onValueChange: (String) -> Unit,
    maxLines: Int,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = textValue,
        maxLines = maxLines,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        placeholder = {
            Text(
                text = placeholder,
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
}

private fun setImageBitmap(context: Context, uri: Uri,imageFileSize:Double): Bitmap {
    val toastMessage = Toast.makeText(context, context.getString(R.string.toast_message_image_resize), Toast.LENGTH_SHORT)

    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(context.contentResolver, uri)
        )
    } else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }
    return if(imageFileSize > 10.0 && imageFileSize < 20.0) {
        toastMessage.show()
        Bitmap.createScaledBitmap(bitmap, bitmap.width/4, bitmap.height/4, true)
    }else if(imageFileSize >3.0 && imageFileSize < 10.0){
        toastMessage.show()
        Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, true)
    }else{
        Bitmap.createScaledBitmap(bitmap, bitmap.width , bitmap.height, true)
    }
}

private fun getImageFileSize(context: Context, uri: Uri): Double {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    var size: Long = 0
    cursor?.use {
        val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
        if (it.moveToFirst()) {
            size = it.getLong(sizeIndex)
        }
    }
    return size / (1024.0 * 1024.0)
}