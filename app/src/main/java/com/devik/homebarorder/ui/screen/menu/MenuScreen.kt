package com.devik.homebarorder.ui.screen.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import com.devik.homebarorder.data.model.CartMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devik.homebarorder.R
import com.devik.homebarorder.data.source.local.database.CategoryEntity
import com.devik.homebarorder.ui.component.drawermenu.DrawerNaviMenu
import com.devik.homebarorder.ui.component.topappbar.MenuIconWithTitleAppBar
import com.devik.homebarorder.ui.dialog.AddMenuDialog
import com.devik.homebarorder.ui.dialog.ManualDialog
import com.devik.homebarorder.ui.theme.DarkGray
import com.devik.homebarorder.ui.theme.LightGray
import com.devik.homebarorder.ui.theme.MediumGray
import com.devik.homebarorder.ui.theme.OrangeSoda
import com.devik.homebarorder.util.TextFormatUtil
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {

    CompositionLocalProvider(
        androidx.lifecycle.compose.LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current,
    ) {
        val viewModel: MenuScreenViewModel = hiltViewModel()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val manualDialogState by viewModel.manualDialogState.collectAsStateWithLifecycle()
        val categoryList by viewModel.allCategoryList.collectAsStateWithLifecycle()
        val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
        val selectedCategoryMenu by viewModel.selectedCategoryMenu.collectAsStateWithLifecycle()
        val cartList by viewModel.cartList.collectAsStateWithLifecycle()
        val addCartDialogState by viewModel.addCartDialogState.collectAsStateWithLifecycle()
        val addTargetCartMenu by viewModel.addTargetCartMenu.collectAsStateWithLifecycle()
        val addTargetCartMenuCount by viewModel.addTargetCartMenuCount.collectAsStateWithLifecycle()
        val allCartPrice by viewModel.allCartPrice.collectAsStateWithLifecycle()
        val allCartCount by viewModel.allCartCount.collectAsStateWithLifecycle()

        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            viewModel.getAllCategoryList()
            viewModel.checkIsFirstSignIn()
            viewModel.getAllMenuList()
        }
        if (manualDialogState) {
            ManualDialog() {
                viewModel.manualDialogDismissRequest()
            }
        }
        if (addCartDialogState && addTargetCartMenu != null) {
            AddMenuDialog(
                menuEntity = addTargetCartMenu!!,
                menuCount = addTargetCartMenuCount,
                onDismissRequest = { viewModel.closeAddCartDialog() },
                onMinusClick = { viewModel.minusTargetMenuCount() },
                onPlusClick = { viewModel.plusTargetMenuCount() },
                onAddClick = { viewModel.addCart() }
            )
        }

        DrawerNaviMenu(
            drawerState = drawerState,
            navController = navController,
            scope = scope
        ) {

            Scaffold(
                topBar = {
                    MenuIconWithTitleAppBar(menuIconOnClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }, title = stringResource(id = R.string.app_name))
                },
                modifier = Modifier.padding(top = 8.dp)
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categoryList) { category ->
                            CategoryItem(
                                category = category,
                                isSelected = category == selectedCategory,
                                onClick = { viewModel.selectCategory(category) }
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.65f)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = LightGray),
                    ) {
                        GridMenuLazyVerticalGrid(
                            menuList = selectedCategoryMenu,
                            onMenuClick = { viewModel.openAddCartDialog(it) }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.7f)
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 1.dp, end = 1.dp, bottom = 1.dp, top = 4.dp)
                                .align(Alignment.TopCenter)
                        ) {
                            items(cartList) { menu ->
                                CartItem(
                                    cartMenuItem = menu,
                                    onDeleteClick = { viewModel.deleteCartMenu(menu) },
                                    onPlusClick = { viewModel.plusMenuCount(menu) },
                                    onMinusClick = { viewModel.minusMenuCount(menu) })
                            }
                        }

                        Divider(
                            thickness = 4.dp, color = OrangeSoda, modifier = Modifier.align(
                                Alignment.TopCenter
                            )
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(LightGray)
                                .align(
                                    Alignment.TopEnd

                                )
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(LightGray)
                                .align(
                                    Alignment.TopStart

                                )
                        )
                        Divider(
                            thickness = 1.dp, color = LightGray, modifier = Modifier
                                .fillMaxWidth()
                                .align(
                                    Alignment.BottomCenter
                                )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.5f)
                                .align(Alignment.CenterStart)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = LightGray)
                        ) {
                            Text(
                                text = stringResource(R.string.menu_screen_text_all_menu_count),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(top = 16.dp, start = 16.dp),
                                fontSize = 24.sp
                            )
                            Text(
                                text = "$allCartCount ${stringResource(R.string.menu_screen_text_menu_count)}",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 16.dp, end = 16.dp),
                                fontSize = 24.sp
                            )
                            Divider(
                                thickness = 2.dp,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 8.dp, end = 8.dp),
                                color = MediumGray
                            )
                            Text(
                                text = stringResource(R.string.menu_screen_text_all_price),
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(bottom = 16.dp, start = 16.dp),
                                fontSize = 24.sp
                            )
                            Text(
                                text = "${TextFormatUtil.thousandsComma.format(allCartPrice)} ${
                                    stringResource(
                                        R.string.price
                                    )
                                }",
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(bottom = 16.dp, end = 16.dp),
                                fontSize = 24.sp,
                                color = OrangeSoda
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.5f)
                                .align(Alignment.CenterEnd)
                                .padding(start = 16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.3f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(color = DarkGray)
                                    .clickable { viewModel.clearCart() }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = stringResource(R.string.content_description_all_clear),
                                    tint = Color.White,
                                    modifier = Modifier
                                        .fillMaxSize(0.6f)
                                        .align(Alignment.TopCenter)
                                        .padding(top = 20.dp)
                                )

                                Text(
                                    text = stringResource(R.string.menu_screen_button_text_all_clear),
                                    color = Color.White,
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 16.dp),
                                    fontSize = 18.sp
                                )
                            }

                            Button(
                                onClick = { /*TODO*/ }, modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.7f)
                                    .align(Alignment.CenterEnd)
                                    .padding(start = 16.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(OrangeSoda)
                            ) {
                                Text(
                                    text = stringResource(R.string.menu_screen_button_text_order),
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    category: CategoryEntity,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (isSelected) {
        Color.White
    } else {
        Color.Black
    }

    val boxColor = if (isSelected) {
        OrangeSoda
    } else {
        Color.White
    }

    val borderColor = if (isSelected) {
        OrangeSoda
    } else {
        Color.Black
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(8.dp)
            .widthIn(min = 160.dp),
        shape = RoundedCornerShape(60.dp),
        border = BorderStroke(1.dp, color = borderColor),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(boxColor)
                .widthIn(min = 160.dp)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.category,
                fontSize = 18.sp,
                color = textColor,
            )
        }
    }
}

@Composable
private fun CartItem(
    cartMenuItem: CartMenuItem,
    onDeleteClick: () -> Unit,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.4f)
                .padding(start = 32.dp),
        ) {
            Text(
                text = cartMenuItem.menuName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = "${TextFormatUtil.thousandsComma.format(cartMenuItem.menuPrice * cartMenuItem.menuCount)} ${
                    stringResource(
                        R.string.price
                    )
                }",
                color = OrangeSoda,
                modifier = Modifier
                    .padding(end = 48.dp)
                    .align(Alignment.CenterEnd),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onMinusClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(color = LightGray),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_minus),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Black
                )
            }

            Text(
                text = cartMenuItem.menuCount.toString(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )

            IconButton(
                onClick = onPlusClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(color = LightGray),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(64.dp))

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(color = OrangeSoda),
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.White
                )
            }
        }
        Divider(
            thickness = 1.dp, color = LightGray, modifier = Modifier
                .fillMaxWidth()
                .align(
                    Alignment.BottomCenter
                )
        )
    }
}