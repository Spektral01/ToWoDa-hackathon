package com.itsc.tuwoda.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itsc.tuwoda.MyFloatingActionButton
import com.itsc.tuwoda.MyViewModel
import com.itsc.tuwoda.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFABWithText(
    text: String = "",
    x: Dp = 0.dp,
    y: Dp = 0.dp,
    paddingCardHorizontal: Dp = 0.dp,
    paddingTextHorizontal: Dp = 0.dp,
    sizeButton: Dp = 0.dp,
    backgroundButton: Int,
    iconButton: Int,
    colorBackgroundButton: Int = R.color.blue_main,
    colorBackgroundText: Int = R.color.blue_main,
    model: MyViewModel = MyViewModel()
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(
                x = x,
                y = y
            ),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = colorBackgroundText)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(horizontal = paddingCardHorizontal),

            ) {
            Text(
                text = text,
                color = Color.White,
                modifier = Modifier.padding(horizontal = paddingTextHorizontal)
            )
        }
        MyFloatingActionButton(
            background = backgroundButton,
            icon = iconButton,
            colorBackground = colorBackgroundButton,
            size = sizeButton,
            state = model.stateMapDialog,
            onState = {state ->
                model.stateMapDialog = state
            }
        )
    }
}