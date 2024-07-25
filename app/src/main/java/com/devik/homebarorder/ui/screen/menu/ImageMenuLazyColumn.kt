package com.devik.homebarorder.ui.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.devik.homebarorder.R
import com.devik.homebarorder.data.source.local.database.MenuEntity
import com.devik.homebarorder.ui.theme.OrangeSoda
import com.devik.homebarorder.util.TextFormatUtil

@Composable
fun ImageMenuLazyColumn(menuList: List<MenuEntity>, onMenuClick: (MenuEntity) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(
            items = menuList,
            key = { menu -> menu.uid }) { menu ->
            ImageColumnMenuItem(
                menu = menu,
                onClick = { onMenuClick(menu) }
            )
        }
    }
}

@Composable
fun ImageColumnMenuItem(
    menu: MenuEntity,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(start = 24.dp, end = 24.dp)
            .clickable { onClick() },
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .height(160.dp),
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
                        model = menu.menuImage,
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
                            .fillMaxWidth()
                            .height(144.dp)
                            .background(color = Color.White)
                    ) {
                        Text(
                            text = menu.menuName,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 8.dp, end = 24.dp)
                                .fillMaxWidth(),
                            fontSize = 20.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = menu.menuInfo, modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 48.dp, end = 24.dp)
                                .fillMaxWidth(),
                            fontSize = 16.sp,
                            color = Color.Gray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = TextFormatUtil.priceTextFormat(
                                menu.menuPrice, stringResource(
                                    R.string.price
                                )
                            ),
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(bottom = 8.dp),
                            fontSize = 18.sp,
                            color = OrangeSoda,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}