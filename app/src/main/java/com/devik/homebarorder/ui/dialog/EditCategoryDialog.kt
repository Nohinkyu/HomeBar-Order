package com.devik.homebarorder.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devik.homebarorder.R
import com.devik.homebarorder.ui.theme.LightGray
import com.devik.homebarorder.ui.theme.OrangeSoda

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryDialog(
    editTextState: String,
    onCategoryChange: (text: String) -> Unit,
    onDismissRequest: () -> Unit,
    onSaveRequest: () -> Unit,
) {

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
                    .background(Color.White)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
            ) {

                Spacer(modifier = Modifier.heightIn(24.dp))

                Text(text = "카테고리 수정", fontSize = 12.sp, modifier = Modifier.padding(start = 24.dp))

                Spacer(modifier = Modifier.heightIn(4.dp))

                OutlinedTextField(
                    value = editTextState,
                    onValueChange = { onCategoryChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(start = 24.dp, end = 24.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Black,
                        focusedBorderColor = Color.Black
                    ),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 20.sp),
                )

                Spacer(modifier = Modifier.heightIn(24.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { onDismissRequest() },
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
                            color = Color.Black
                        )
                    }
                    Button(
                        onClick = { onSaveRequest() },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxWidth(0.5f)
                            .padding(start = 16.dp, end = 16.dp)
                            .heightIn(48.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OrangeSoda)
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_button_save),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}