package com.devik.homebarorder.ui.component.image

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.devik.homebarorder.R

@Composable
fun AsyncImageWithDefaultIcon(image: Bitmap?, contentDescription: String, modifier: Modifier) {
    if (image == null) {
        Image(
            painter = painterResource(id = R.drawable.ic_image_not_supported),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop,
        )
    } else {
        AsyncImage(
            model = image,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop,
        )
    }
}