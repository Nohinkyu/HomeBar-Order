package com.devik.homebarorder.ui.dialog

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.devik.homebarorder.R
import com.devik.homebarorder.data.source.local.database.MenuEntity
import com.devik.homebarorder.extension.setImmersiveMode
import com.devik.homebarorder.ui.theme.LightGray
import com.devik.homebarorder.ui.theme.OrangeSoda
import com.devik.homebarorder.util.TextFormatUtil

@Composable
fun AddMenuDialog(
    menuEntity: MenuEntity,
    menuCount: Int,
    onDismissRequest: () -> Unit,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    onAddClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest, properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        val view = LocalView.current
        view.setImmersiveMode()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = menuEntity.menuImage,
                            contentDescription = stringResource(R.string.content_description_menu_image),
                            modifier = Modifier
                                .width(150.dp)
                                .height(200.dp)
                                .clip(shape = RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = menuEntity.menuName,
                                    fontSize = 18.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                )

                                Spacer(modifier = Modifier.width(24.dp))

                                IconButton(
                                    onClick = onMinusClick,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(color = LightGray),
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_minus),
                                        contentDescription = stringResource(R.string.content_description_minus_button),
                                        modifier = Modifier.fillMaxSize(),
                                        tint = Color.Black
                                    )
                                }
                                Text(
                                    text = menuCount.toString(),
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
                                        contentDescription = stringResource(R.string.content_description_plus_button),
                                        modifier = Modifier.fillMaxSize(),
                                        tint = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Divider(thickness = 2.dp, color = OrangeSoda)
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = menuEntity.menuInfo,
                                fontSize = 16.sp,
                                color = Gray,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 40.dp)
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = TextFormatUtil.priceTextFormat(
                                    menuEntity.menuPrice * menuCount, stringResource(
                                        R.string.price
                                    )
                                ),
                                fontSize = 20.sp, color = OrangeSoda
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onDismissRequest,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .fillMaxWidth(0.5f)
                            .padding(start = 16.dp, end = 16.dp)
                            .heightIn(48.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LightGray),
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_button_cancel),
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                    Button(
                        onClick = {
                            onAddClick()
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxWidth(0.5f)
                            .padding(start = 16.dp, end = 16.dp)
                            .heightIn(48.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OrangeSoda)
                    ) {
                        Text(
                            text = stringResource(R.string.button_add_menu),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}