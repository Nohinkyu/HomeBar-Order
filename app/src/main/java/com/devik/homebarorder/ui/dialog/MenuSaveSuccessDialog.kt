package com.devik.homebarorder.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devik.homebarorder.R
import com.devik.homebarorder.extension.setImmersiveMode
import com.devik.homebarorder.extension.throttledClickable
import com.devik.homebarorder.ui.theme.LightGray
import com.devik.homebarorder.ui.theme.OrangeSoda

@Composable
fun MenuSaveSuccessDialog(
    body: String,
    yesButtonText: String,
    onDismissRequest: () -> Unit,
    onYesClickRequest: () -> Unit,
    onCancelClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        val coroutineScope = rememberCoroutineScope()
        val view = LocalView.current
        view.setImmersiveMode()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(5.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.heightIn(8.dp))

                Text(
                    text = body,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.heightIn(16.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .fillMaxWidth(0.5f)
                            .padding(start = 16.dp, end = 16.dp)
                            .heightIn(48.dp)
                            .background(color = LightGray, shape = RoundedCornerShape(5.dp))
                            .throttledClickable(
                                coroutineScope = coroutineScope,
                                onClick = onCancelClick
                            ),
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_button_cancel),
                            color = Color.Black,
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxWidth(0.5f)
                            .padding(start = 16.dp, end = 16.dp)
                            .heightIn(48.dp)
                            .background(color = OrangeSoda, shape = RoundedCornerShape(5.dp))
                            .throttledClickable(
                                coroutineScope = coroutineScope,
                                onClick = onYesClickRequest
                            ),
                    ) {
                        Text(
                            text = yesButtonText,
                            color = Color.White,
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                        )
                    }
                }
            }
        }
    }
}