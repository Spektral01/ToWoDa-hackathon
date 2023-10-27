package com.itsc.tuwoda

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDialog(
    name: String,
    colorName: Color,
    onState: (String) -> Unit,
    state: String,
) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            color = colorName,
            modifier = Modifier.offset(x = 15.dp)
        )
        TextField(
            value = state,
            onValueChange = {text ->
                onState(text)
            },
            trailingIcon = {
                MyFloatingActionButton(
                    background = R.drawable.ellipsefull,
                    icon = R.drawable.geoicon,
                    size = 50.dp,
                    scaleX = (-5).dp,
                    colorBackground = R.color.blue_main_alfa
                )
            },
            shape = RoundedCornerShape(100.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.White
            )
        )
    }

}