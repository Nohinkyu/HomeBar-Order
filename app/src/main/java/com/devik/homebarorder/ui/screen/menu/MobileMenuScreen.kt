package com.devik.homebarorder.ui.screen.menu

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devik.homebarorder.R
import com.devik.homebarorder.ui.component.drawermenu.DrawerNaviMenu
import com.devik.homebarorder.ui.component.topappbar.MenuIconWithTitleAppBar
import com.devik.homebarorder.ui.dialog.AddMenuDialog
import com.devik.homebarorder.ui.dialog.ManualDialog
import com.devik.homebarorder.ui.dialog.OrderInProgressDialog
import com.devik.homebarorder.ui.dialog.OrderListDialog
import com.devik.homebarorder.ui.dialog.OrderResultDialog
import com.devik.homebarorder.ui.theme.DarkGray
import com.devik.homebarorder.ui.theme.LightGray
import com.devik.homebarorder.ui.theme.MediumGray
import com.devik.homebarorder.ui.theme.OrangeSoda
import com.devik.homebarorder.util.Constants
import com.devik.homebarorder.util.TextFormatUtil
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobileMenuScreen(navController: NavController) {

    CompositionLocalProvider(
        androidx.lifecycle.compose.LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current,
    ) {

        val context = LocalContext.current
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
        val orderListDialogState by viewModel.orderListDialogState.collectAsStateWithLifecycle()
        val isOrderInProgress by viewModel.isOrderInProgress.collectAsStateWithLifecycle()
        val isOrderSuccess by viewModel.isOrderSuccess.collectAsStateWithLifecycle()
        val isOrderFail by viewModel.isOrderFail.collectAsStateWithLifecycle()
        val menuListFormatState by viewModel.menuListFormatState.collectAsStateWithLifecycle()
        val orderNumberState by viewModel.orderNumberState.collectAsStateWithLifecycle()

        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            with(viewModel) {
                getAllCategoryList()
                checkIsFirstSignIn()
                getAllMenuList()
                getMenuListFormatState()
            }
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

        if (orderListDialogState) {
            OrderListDialog(
                cartMenuItemList = cartList,
                onDismissRequest = { viewModel.closeOrderListDialog() },
                cartMenuItemAllPrice = allCartPrice,
                cartMenuItemCount = allCartCount,
                onOrderClick = {
                    viewModel.insertOrder()
                    viewModel.closeOrderListDialog()
                }
            )
        }

        if (isOrderInProgress) {
            OrderInProgressDialog()
        }

        if (isOrderSuccess) {
            OrderResultDialog(
                onDismissRequest = { viewModel.closeOrderSuccessDialog() },
                resultMessageTitle = stringResource(R.string.order_result_dialog_success_title),
                resultMessageBody = stringResource(R.string.order_result_dialog_success_body),
                orderNumber = orderNumberState
            )
        }

        if (isOrderFail) {
            OrderResultDialog(
                onDismissRequest = { viewModel.closeOrderFailDialog() },
                resultMessageTitle = stringResource(R.string.order_result_dialog_fail_title),
                resultMessageBody = stringResource(R.string.order_result_dialog_fail_body).trimMargin()
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
                            .fillMaxHeight(0.6f)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = LightGray),
                    ) {
                        when (menuListFormatState) {
                            Constants.IMAGE_GRID_STATE -> {
                                GridMenuLazyVerticalGrid(
                                    menuList = selectedCategoryMenu,
                                    onMenuClick = { viewModel.openAddCartDialog(it) }
                                )
                            }

                            Constants.IMAGE_LIST_STATE -> {
                                ImageMenuLazyColumn(
                                    menuList = selectedCategoryMenu,
                                    onMenuClick = { viewModel.openAddCartDialog(it) }
                                )
                            }

                            Constants.NO_IMAGE_LIST_STATE -> {
                                ImageLessMenuLazyColumn(
                                    menuList = selectedCategoryMenu,
                                    onMenuClick = { viewModel.openAddCartDialog(it) }
                                )
                            }

                            else -> {
                                GridMenuLazyVerticalGrid(
                                    menuList = selectedCategoryMenu,
                                    onMenuClick = { viewModel.openAddCartDialog(it) }
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f)
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.TopCenter)
                                .border(width = 2.dp, color = LightGray, shape = RectangleShape)
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
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.5f)
                                .align(Alignment.CenterStart)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = LightGray),
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.menu_screen_text_all_menu_count),
                                    modifier = Modifier
                                        .padding(top = 16.dp, start = 16.dp),
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "$allCartCount ${stringResource(R.string.menu_screen_text_menu_count)}",
                                    modifier = Modifier
                                        .padding(top = 16.dp, end = 16.dp),
                                    fontSize = 14.sp
                                )
                            }
                            Divider(
                                thickness = 2.dp,
                                modifier = Modifier
                                    .padding(8.dp),
                                color = MediumGray
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.menu_screen_text_all_price),
                                    modifier = Modifier
                                        .padding(bottom = 16.dp, start = 16.dp),
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = TextFormatUtil.priceTextFormat(
                                        allCartPrice,
                                        stringResource(R.string.price)
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 16.dp, end = 16.dp),
                                    fontSize = 14.sp,
                                    color = OrangeSoda
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.5f)
                                .align(Alignment.CenterEnd)
                                .padding(start = 16.dp)
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.3f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(color = DarkGray)
                                    .clickable { viewModel.clearCart() },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = stringResource(R.string.content_description_all_clear),
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .padding(top = 16.dp)
                                )
                                Spacer(modifier = Modifier.size(16.dp))
                                Text(
                                    text = stringResource(R.string.menu_screen_button_text_all_clear),
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(bottom = 16.dp),
                                    fontSize = 14.sp
                                )
                            }
                            Button(
                                onClick = {
                                    if (cartList.isEmpty()) {
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.message_is_cart_blank),
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    } else {
                                        viewModel.openOrderListDialog()
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.7f)
                                    .align(Alignment.CenterEnd)
                                    .padding(start = 16.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(OrangeSoda)
                            ) {
                                Text(
                                    text = stringResource(R.string.menu_screen_button_text_order),
                                    fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    maxLines = 1,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}