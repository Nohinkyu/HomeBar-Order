package com.devik.homebarorder.ui.screen.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.devik.homebarorder.R
import com.devik.homebarorder.data.source.local.database.MenuEntity
import com.devik.homebarorder.ui.theme.MediumGray
import com.devik.homebarorder.ui.theme.OrangeSoda
import com.devik.homebarorder.util.TextFormatUtil

@Composable
fun GridMenuLazyVerticalGrid(menuList: List<MenuEntity>, onMenuClick: (MenuEntity) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = menuList,
            key = { menu -> menu.uid }) { menu ->
            GridMenuItem(
                menu = menu,
                onClick = { onMenuClick(menu) }
            )
        }
    }
}

@Composable
fun GridMenuItem(menu: MenuEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.25f)
            .fillMaxHeight(0.33f)
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, color = MediumGray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = menu.menuImage,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(0.75f)
                    .aspectRatio(1f / 1f)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = TextFormatUtil.priceTextFormat(
                    menu.menuPrice, stringResource(
                        R.string.price
                    )
                ),
                fontSize = 16.sp,
                color = OrangeSoda
            )
            Text(
                text = menu.menuName,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }
    }
}