package com.devik.homebarorder.ui.screen.managemenu

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devik.homebarorder.R
import com.devik.homebarorder.data.source.local.database.CategoryEntity
import com.devik.homebarorder.data.source.local.database.MenuEntity
import com.devik.homebarorder.ui.component.navigation.NavigationRoute
import com.devik.homebarorder.ui.component.topappbar.BackAndSearchIconAppBar
import com.devik.homebarorder.ui.component.topappbar.SearchAppBar
import com.devik.homebarorder.ui.dialog.YesOrNoDialog
import com.devik.homebarorder.ui.theme.LightGray
import com.devik.homebarorder.ui.theme.MediumGray
import com.devik.homebarorder.ui.theme.OrangeSoda
import com.devik.homebarorder.util.TextFormatUtil

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ManageMenuScreen(navController: NavController) {

    CompositionLocalProvider(
        androidx.lifecycle.compose.LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current,
    ) {
        val viewModel: ManageMenuViewModel = hiltViewModel()
        val selectedCategoryMenu by viewModel.selectedCategoryMenu.collectAsStateWithLifecycle()
        val allCategoryList by viewModel.allCategoryList.collectAsStateWithLifecycle()
        val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()
        val isAllCategorySelected by viewModel.isAllCategorySelected.collectAsStateWithLifecycle()
        val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
        val searchAppBarState by viewModel.searchAppBarState.collectAsStateWithLifecycle()
        val searchTextState by viewModel.searchTextState.collectAsStateWithLifecycle()

        viewModel.getAllCategoryList()
        viewModel.getAllMenuList()

        Scaffold(
            topBar = {
                if (!searchAppBarState) {
                    BackAndSearchIconAppBar(
                        title = stringResource(R.string.top_appbar_title_menu),
                        searchIconClick = { viewModel.openSearchAppBar() },
                        backIconClick = { navController.navigateUp() })
                } else {
                    SearchAppBar(
                        onBackClick = { viewModel.closeSearchAppBar() },
                        text = searchTextState,
                        onValueChange = { viewModel.onSearchTextChange(it) },
                        onSearchClick = { viewModel.searchMenu(searchTextState) }
                    )
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Divider(thickness = 1.dp, color = LightGray)

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        item {
                            CategoryItemHeader(isSelected = isAllCategorySelected, onClick = {
                                if (!isAllCategorySelected) {
                                    viewModel.allMenuSelect()
                                }
                            })
                        }
                        items(allCategoryList) { item ->
                            CategoryItem(categoryEntity = item,
                                isSelected = item == selectedCategory,
                                onClick = {
                                    viewModel.getSelectedCategoryMenu(item)
                                })
                        }
                    }
                    Divider(thickness = 1.dp, color = LightGray)

                    Spacer(modifier = Modifier.size(16.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        items(
                            items = selectedCategoryMenu,
                            key = { menu -> menu.uid }) { menuEntity ->
                            MenuItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItemPlacement(tween(durationMillis = 1000)),
                                menuEntity = menuEntity,
                                onEditClick = {
                                    navController.navigate("${NavigationRoute.MENU_EDITOR_SCREEN}/${menuEntity.uid}")
                                },
                                onDeleteClick = {
                                    viewModel.setDeleteTargetMenu(menuEntity)
                                    viewModel.showDeleteDialog()
                                })
                            if (dialogState) {
                                YesOrNoDialog(
                                    body = stringResource(R.string.dialog_message_delete_menu),
                                    yesButtonText = stringResource(R.string.dialog_button_delete),
                                    onDismissRequest = {
                                        with(viewModel) {
                                            cancelDeleteMenu()
                                            closeDeleteDialog()
                                        }
                                    },
                                    onYesClickRequest = {
                                        with(viewModel) {
                                            deleteMenu()
                                            closeDeleteDialog()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                IconButton(
                    onClick = { navController.navigate(NavigationRoute.MENU_EDITOR_SCREEN) },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(80.dp)
                        .padding(end = 24.dp, bottom = 24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = stringResource(R.string.content_description_add_menu_button),
                        modifier = Modifier.size(80.dp),
                        tint = OrangeSoda
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    categoryEntity: CategoryEntity,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val boxModifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .clickable(onClick = onClick)
        .background(color = Color.White)
        .then(
            if (isSelected) Modifier.drawBehind {
                val borderStroke = 2.dp.toPx()
                val y = size.height - borderStroke / 2
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = borderStroke
                )
            } else Modifier
        )

    Box(
        modifier = boxModifier

    ) {
        Text(
            text = categoryEntity.category,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 12.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )
    }
}

@Composable
private fun CategoryItemHeader(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .heightIn(48.dp)
            .width(80.dp)
            .clickable(onClick = onClick)
            .background(color = Color.White)
    ) {
        Text(
            text = stringResource(R.string.menu_item_all_category),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 12.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .height(2.dp)
                    .align(
                        Alignment.BottomCenter
                    )
            )
        }
    }
}

@Composable
private fun MenuItem(
    menuEntity: MenuEntity,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier
) {
    val imageBitmap by remember { mutableStateOf(menuEntity.menuImage) }
    var expandStatus by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(4.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .height(160.dp)
                .padding(start = 24.dp, end = 24.dp),
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(color = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .align(Alignment.CenterStart)
                ) {
                    AsyncImage(
                        model = imageBitmap,
                        contentDescription = stringResource(R.string.content_description_menu_image),
                        modifier = Modifier
                            .size(height = 144.dp, width = 115.dp)
                            .padding(start = 8.dp)
                            .clip(shape = RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(144.dp)
                            .background(color = Color.White)
                    ) {
                        Text(
                            text = menuEntity.menuName, modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 8.dp),
                            fontSize = 18.sp
                        )
                        Text(
                            text = menuEntity.menuInto, modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 48.dp),
                            fontSize = 14.sp,
                            color = Gray
                        )
                        Text(
                            text = "${TextFormatUtil.thousandsComma.format(menuEntity.menuPrice)} ${stringResource(
                                R.string.price
                            )}",
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(bottom = 8.dp),
                            fontSize = 16.sp,
                            color = OrangeSoda
                        )
                    }
                }
                IconButton(
                    onClick = { expandStatus = true }, modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 8.dp, top = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_point_menu),
                        contentDescription = ""
                    )
                    DropdownMenu(
                        expanded = expandStatus,
                        onDismissRequest = { expandStatus = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.drop_down_menu_item_edit), color = Color.Black) },
                            onClick = {
                                onEditClick()
                                expandStatus = false
                            })
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.drop_down_menu_item_delete), color = Color.Red) },
                            onClick = {
                                onDeleteClick()
                                expandStatus = false
                            })
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
    }
}