package com.devik.homebarorder.ui.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devik.homebarorder.R
import com.devik.homebarorder.data.model.CartMenuItem
import com.devik.homebarorder.ui.theme.LightGray
import com.devik.homebarorder.ui.theme.OrangeSoda
import com.devik.homebarorder.util.TextFormatUtil

@Composable
fun CartItem(
    cartMenuItem: CartMenuItem,
    onDeleteClick: () -> Unit,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = cartMenuItem.menuName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 32.dp)
        )
        Text(
            text = TextFormatUtil.priceTextFormat(
                cartMenuItem.menuPrice * cartMenuItem.menuCount, stringResource(
                    R.string.price
                )
            ),
            color = OrangeSoda,
            modifier = Modifier
                .padding(end = 48.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp
        )

        Row(
            modifier = Modifier
                .height(64.dp),
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
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .width(32.dp)
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
            Spacer(modifier = Modifier.width(32.dp))
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
    }
    Divider(thickness = 1.dp, color = LightGray, modifier = Modifier.fillMaxWidth())
}