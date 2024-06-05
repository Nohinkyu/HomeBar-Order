package com.devik.homebarorder.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devik.homebarorder.R
import com.devik.homebarorder.ui.theme.OrangeSoda

@Composable
fun ManualDialog(onDismissRequest: () -> Unit) {

    val context = LocalContext.current

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
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
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.heightIn(8.dp))

                Text(
                    text = context.getString(R.string.dialog_message_manual_title),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.heightIn(8.dp))

                Text(
                    text = context.getString(R.string.dialog_message_manual_text),
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.heightIn(16.dp))

                HeadRowText(
                    head = context.getString(R.string.dialog_message_manual_header_1),
                    body = context.getString(R.string.dialog_message_manual_body_1)
                )
                HeadRowText(
                    head = context.getString(R.string.dialog_message_manual_header_2),
                    body = context.getString(R.string.dialog_message_manual_body_2)
                )
                HeadRowText(
                    head = context.getString(R.string.dialog_message_manual_header_3),
                    body = context.getString(R.string.dialog_message_manual_body_3)
                )
                HeadRowText(
                    head = context.getString(R.string.dialog_message_manual_header_4),
                    body = context.getString(R.string.dialog_message_manual_body_4)
                )
                HeadRowText(
                    head = context.getString(R.string.dialog_message_manual_header_5),
                    body = context.getString(R.string.dialog_message_manual_body_5)
                )
                HeadRowText(
                    head = context.getString(R.string.dialog_message_manual_header_6),
                    body = context.getString(R.string.dialog_message_manual_body_6)
                )

                Button(
                    onClick = { onDismissRequest() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeSoda)
                ) {
                    Text(text = context.getString(R.string.dialog_button_manual_start))
                }
            }
        }
    }
}

@Composable
fun HeadRowText(head: String, body: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(text = head, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = body, fontSize = 16.sp)
    }
    Spacer(modifier = Modifier.heightIn(16.dp))
}
