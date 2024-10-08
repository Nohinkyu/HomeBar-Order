package com.devik.homebarorder.ui.screen.setting

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devik.homebarorder.BuildConfig
import com.devik.homebarorder.R
import com.devik.homebarorder.ui.component.navigation.NavigationRoute
import com.devik.homebarorder.ui.component.topappbar.BackIconWithTitleAppBar
import com.devik.homebarorder.ui.dialog.EditTextDialog
import com.devik.homebarorder.ui.dialog.YesOrNoDialog
import com.devik.homebarorder.ui.theme.DarkGray
import com.devik.homebarorder.ui.theme.LightGray
import com.devik.homebarorder.ui.theme.MediumGray
import com.devik.homebarorder.ui.theme.OrangeSoda

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController) {
    CompositionLocalProvider(
        androidx.lifecycle.compose.LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current,
    ) {
        val viewModel: SettingViewModel = hiltViewModel()
        val isImageGridChecked by viewModel.isImageGridChecked.collectAsStateWithLifecycle()
        val isImageListChecked by viewModel.isImageListChecked.collectAsStateWithLifecycle()
        val isNoImageListChecked by viewModel.isNoImageListChecked.collectAsStateWithLifecycle()
        val signDialogState by viewModel.signOutDialogState.collectAsStateWithLifecycle()
        val isManageMode by viewModel.isManageMode.collectAsStateWithLifecycle()
        val tableNumberState by viewModel.tableNumberState.collectAsStateWithLifecycle()
        val editTableNumberTextState by viewModel.editTableNumberTextState.collectAsStateWithLifecycle()
        val editTableNumberDialogState by viewModel.editTableNumberDialogState.collectAsStateWithLifecycle()
        val changeManageModeDialogState by viewModel.changeManageModeDialogState.collectAsStateWithLifecycle()
        val changeManageModeTextState by viewModel.changeManageModeTextState.collectAsStateWithLifecycle()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            viewModel.checkListState()
        }

        if (signDialogState) {
            YesOrNoDialog(body = stringResource(R.string.sign_out_dialog_message).trimMargin(),
                yesButtonText = stringResource(R.string.sign_out_dialog_button_sign_out),
                onDismissRequest = { viewModel.closeSignOutDialog() },
                onYesClickRequest = {
                    viewModel.closeSignOutDialog()
                    viewModel.signOut()
                    navController.navigate(NavigationRoute.SIGN_IN_SCREEN) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                })
        }

        if (editTableNumberDialogState) {
            EditTextDialog(
                editTextTitle = stringResource(R.string.setting_screen_text_edit_table_number),
                yesButtonText = stringResource(R.string.dialog_button_save),
                editTextState = editTableNumberTextState,
                onTextChange = { viewModel.onEditTableNumberTextChange(it) },
                onDismissRequest = { viewModel.closeEditTableNumberDialog() },
                onSaveRequest = { viewModel.changeTableNumber() }
            )
        }

        if (changeManageModeDialogState) {
            EditTextDialog(
                editTextTitle = stringResource(R.string.setting_screen_text_manage_mode_message),
                yesButtonText = stringResource(R.string.dialog_button_yes),
                editTextState = changeManageModeTextState,
                onTextChange = { viewModel.onEditManageModeTextChange(it) },
                onDismissRequest = { viewModel.closeChangeManageModeDialog() },
                onSaveRequest = {
                    if (viewModel.changeManageMode()) {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.setting_screen_text_manage_mode),
                                Toast.LENGTH_LONG
                            )
                            .show()
                    } else {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.setting_screen_text_email_mismatch),
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                    viewModel.closeChangeManageModeDialog()
                }
            )
        }

        Scaffold(
            topBar = {
                BackIconWithTitleAppBar(
                    title = stringResource(R.string.top_appbsr_title_setting),
                    onBackIconClick = { navController.navigateUp() }
                )
            },
            modifier = Modifier.padding(top = 8.dp)
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Divider(thickness = 1.dp, color = LightGray)

                Text(
                    text = stringResource(R.string.setting_screen_text_menu_format),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 24.sp,
                    color = OrangeSoda
                )
                ListFormatCheckbox(
                    painterResource = R.drawable.ic_grid_list,
                    checked = isImageGridChecked,
                    listName = stringResource(R.string.setting_screen_text_image_grid),
                    onClick = { viewModel.imageGridCheck() })
                ListFormatCheckbox(
                    painterResource = R.drawable.ic_image_list,
                    checked = isImageListChecked,
                    listName = stringResource(R.string.setting_screen_text_image_list),
                    onClick = { viewModel.imageListCheck() })
                ListFormatCheckbox(
                    painterResource = R.drawable.ic_no_image_list,
                    checked = isNoImageListChecked,
                    listName = stringResource(R.string.setting_screen_text_no_image_list),
                    onClick = { viewModel.noImageListCheck() })

                Spacer(modifier = Modifier.size(16.dp))

                Divider(thickness = 1.dp, color = LightGray)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (!isManageMode) {
                                Toast
                                    .makeText(
                                        context,
                                        context.getString(R.string.setting_screen_text_is_not_manage_mode),
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            } else {
                                viewModel.openEditTableNumberDialog()
                            }
                        }) {
                    Text(
                        text = stringResource(R.string.setting_screen_text_table_number),
                        modifier = Modifier
                            .padding(16.dp),
                        fontSize = 24.sp
                    )

                    Text(
                        text = tableNumberState,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        fontSize = 24.sp,
                        maxLines = 1,
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Divider(thickness = 1.dp, color = LightGray)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent =
                                Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.GOOGLE_FORMS_URL))
                            context.startActivity(intent)
                        }
                ) {
                    Text(
                        text = stringResource(R.string.setting_screen_text_google_forms),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        fontSize = 24.sp
                    )
                }
                Divider(thickness = 1.dp, color = LightGray)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isManageMode) {
                                viewModel.openSignOutDialog()
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        context.getString(R.string.setting_screen_text_is_not_manage_mode),
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            }
                        }) {
                    Text(
                        text = stringResource(R.string.setting_screen_text_sign_out),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        fontSize = 24.sp
                    )
                }
                Divider(thickness = 1.dp, color = LightGray)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.setting_screen_text_manage_mode),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        fontSize = 24.sp
                    )
                    Switch(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp),
                        checked = isManageMode,
                        onCheckedChange = {
                            if (isManageMode) {
                                viewModel.changeUnManageMode()
                            } else {
                                viewModel.openChangeManageModeDialog()
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = LightGray,
                            checkedTrackColor = OrangeSoda,
                            uncheckedThumbColor = DarkGray,
                            uncheckedTrackColor = MediumGray,
                        )
                    )
                }
                Divider(thickness = 1.dp, color = LightGray)
            }
        }
    }
}

@Composable
private fun ListFormatCheckbox(
    checked: Boolean,
    painterResource: Int,
    listName: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(painterResource),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Checkbox(
            checked = checked, onCheckedChange = { onClick() },
            colors = CheckboxDefaults.colors(
                checkedColor = OrangeSoda
            )
        )
        Text(text = listName, fontSize = 18.sp)
    }
}