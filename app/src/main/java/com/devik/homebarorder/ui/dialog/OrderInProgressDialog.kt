package com.devik.homebarorder.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devik.homebarorder.R
import com.devik.homebarorder.extension.setImmersiveMode
import com.devik.homebarorder.ui.theme.OrangeSoda

@Composable
fun OrderInProgressDialog() {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        val view = LocalView.current
        view.setImmersiveMode()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(5.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.order_in_progress_dialog_body),
                color = Color.Black,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.size(24.dp))
            CircularProgressIndicator(
                modifier = Modifier
                    .width(32.dp)
                    .padding(bottom = 16.dp),
                color = OrangeSoda,
            )
        }
    }
}