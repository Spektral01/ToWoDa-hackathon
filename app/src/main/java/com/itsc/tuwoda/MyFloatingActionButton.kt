package com.itsc.tuwoda

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MyFloatingActionButton(
    background: Int,
    icon: Int,
    padding: Dp = 0.dp,
    size: Dp = 55.dp,
    scaleX: Dp = 0.dp,
    scaleY: Dp = 0.dp,
    onState: (Boolean) -> Unit = {},
    state: Boolean = false,
    colorBackground: Int = R.color.blue_main,
    tint: Color = Color.White
) {
    FloatingActionButton(
        onClick = {
            onState(!state)
        },
        modifier = Modifier
            .offset(x = scaleX, y = scaleY)
            .padding(padding)
            .background(Color.Transparent)
            .size(size)
            /*.paint(
                painter = painterResource(id = background),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(colorResource(id = colorBackground)),
            ),*/,
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        ) {
        Icon(
            painter = painterResource(id = background),
            contentDescription = "backButtonFloat",
            tint = colorResource(id = colorBackground)
        )

        Icon(
            painter = painterResource(id = icon),
            contentDescription = "moreVert",
            tint = tint,
            modifier = Modifier
                .padding(padding)
                .size(size * 0.6f)
                .background(Color.Transparent)
        )
    }
}