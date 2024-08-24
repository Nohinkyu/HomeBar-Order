package com.devik.homebarorder.ui.screen.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devik.homebarorder.data.source.local.database.CategoryEntity
import com.devik.homebarorder.ui.theme.OrangeSoda

@Composable
fun CategoryItem(
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
            .widthIn(min = 160.dp , max = 180.dp),
        shape = RoundedCornerShape(60.dp),
        border = BorderStroke(1.dp, color = borderColor),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(boxColor)
                .widthIn(min = 160.dp , max = 180.dp)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.category,
                fontSize = 18.sp,
                color = textColor,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}