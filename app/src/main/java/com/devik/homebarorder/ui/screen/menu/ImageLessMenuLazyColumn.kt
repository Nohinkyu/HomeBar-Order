package com.devik.homebarorder.ui.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devik.homebarorder.R
import com.devik.homebarorder.data.source.local.database.MenuEntity
import com.devik.homebarorder.ui.theme.MediumGray
import com.devik.homebarorder.ui.theme.OrangeSoda
import com.devik.homebarorder.util.TextFormatUtil

@Composable
fun ImageLessMenuLazyColumn(menuList: List<MenuEntity>, onMenuClick: (MenuEntity) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(
            items = menuList,
            key = { menu -> menu.uid }) { menu ->
            ImageLessColumnMenuItem(
                menu = menu,
                onClick = { onMenuClick(menu) }
            )
        }
    }
}

@Composable
fun ImageLessColumnMenuItem(menu: MenuEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)) {
            Text(
                text = menu.menuName,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 16.dp)
            )

            Text(
                text = menu.menuInto,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 16.dp),
                color = MediumGray
            )

            Text(
                text = "${TextFormatUtil.thousandsComma.format(menu.menuPrice)} ${
                    stringResource(
                        R.string.price
                    )
                }",
                fontSize = 18.sp,
                color = OrangeSoda,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)
            )
        }
    }
}