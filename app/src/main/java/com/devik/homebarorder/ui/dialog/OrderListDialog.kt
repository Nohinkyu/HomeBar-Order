package com.devik.homebarorder.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devik.homebarorder.R
import com.devik.homebarorder.data.model.CartMenuItem
import com.devik.homebarorder.extension.setImmersiveMode
import com.devik.homebarorder.ui.theme.DarkGray
import com.devik.homebarorder.ui.theme.LightGray
import com.devik.homebarorder.ui.theme.OrangeSoda
import com.devik.homebarorder.util.TextFormatUtil

@Composable
fun OrderListDialog(
    cartMenuItemList: List<CartMenuItem>,
    onDismissRequest: () -> Unit,
    cartMenuItemCount: Int,
    cartMenuItemAllPrice: Int,
    onOrderClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        val view = LocalView.current
        view.setImmersiveMode()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White, shape = RoundedCornerShape(5.dp))
            ) {
                Text(
                    text = stringResource(R.string.order_dialog_title),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Divider(thickness = 4.dp, color = OrangeSoda)
                Text(
                    text = stringResource(R.string.order_dialog_body),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp),
                    color = OrangeSoda,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .padding(16.dp)
                        .border(width = 2.dp, color = LightGray, shape = RectangleShape)
                ) {
                    Divider(thickness = 4.dp, color = DarkGray)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = LightGray)
                    ) {
                        Text(
                            text = stringResource(R.string.order_dialog_menu_name),
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                                .align(Alignment.CenterStart)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.45f)
                                .align(Alignment.CenterEnd),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.order_dialog_menu_count),
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 16.dp, bottom = 16.dp)
                            )
                            Text(
                                text = stringResource(R.string.order_dialog_menu_price),
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(end = 16.dp, top = 16.dp, bottom = 16.dp)
                            )
                        }
                    }
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(cartMenuItemList) { cartDialogItem ->
                            OrderListDialogItem(cartMenuItem = cartDialogItem)
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = LightGray, shape = RoundedCornerShape(5.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
                            .align(Alignment.TopStart),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.order_dialog_all_count),
                            fontSize = 20.sp
                        )
                        Text(
                            text = "$cartMenuItemCount ${stringResource(R.string.count)}",
                            fontSize = 20.sp,
                            color = OrangeSoda
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
                            .align(Alignment.TopEnd),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.order_dialog_all_price),
                            fontSize = 20.sp
                        )
                        Text(
                            text = TextFormatUtil.priceTextFormat(
                                cartMenuItemAllPrice, stringResource(
                                    R.string.price
                                )
                            ),
                            fontSize = 20.sp, color = OrangeSoda
                        )
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        onClick = { onDismissRequest() },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(start = 16.dp, end = 16.dp)
                            .align(Alignment.TopStart),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LightGray)
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_button_cancel),
                            color = Color.Black,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(
                                top = 14.dp,
                                bottom = 14.dp
                            )
                        )
                    }

                    Button(
                        onClick = { onOrderClick() },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(start = 16.dp, end = 16.dp)
                            .align(Alignment.TopEnd),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OrangeSoda)
                    ) {
                        Text(
                            text = stringResource(R.string.order_dialog_order),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(
                                top = 14.dp,
                                bottom = 14.dp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderListDialogItem(cartMenuItem: CartMenuItem) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = cartMenuItem.menuName,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                .align(Alignment.CenterStart)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(0.45f)
                .align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${cartMenuItem.menuCount}",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 12.dp, top = 16.dp, bottom = 16.dp),
                color = OrangeSoda
            )
            Text(
                text = TextFormatUtil.thousandsComma.format(cartMenuItem.menuCount * cartMenuItem.menuPrice),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp, bottom = 16.dp),
                color = OrangeSoda
            )
        }
        Divider(
            thickness = 1.dp,
            color = LightGray,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}