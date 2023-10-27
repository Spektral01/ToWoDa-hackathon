package com.itsc.tuwoda

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MyBottomBar(
    model: MyViewModel
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(30.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ellipse),
                contentDescription = "ell1",
                tint = if (model.stateMap) colorResource(id = R.color.blue_main)
                else Color.Transparent,
                modifier = Modifier.offset(y = (15).dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ellipse),
                contentDescription = "ell2",
                tint = if (model.stateSailing) colorResource(id = R.color.blue_main)
                else Color.Transparent,
                modifier = Modifier.offset(y = (15).dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ellipse),
                contentDescription = "ell3",
                tint = if (model.stateRoutes) colorResource(id = R.color.blue_main)
                else Color.Transparent,
                modifier = Modifier.offset(y = (15).dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ellipse),
                contentDescription = "ell4",
                tint = if (model.stateMe) colorResource(id = R.color.blue_main)
                else Color.Transparent,
                modifier = Modifier.offset(y = (15).dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.blue_main))
                .height(50.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.map),
                contentDescription = "map",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        model.updateStateMap()
                    }
                    .offset(y = if (model.stateMap) (-15).dp else 0.dp)
                    .padding(14.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.sailing),
                contentDescription = "sailing",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        model.updateStateSailing()
                    }
                    .offset(y = if (model.stateSailing) (-15).dp else 0.dp)
                    .padding(14.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.routes),
                contentDescription = "routes",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        model.updateStateRoutes()
                    }
                    .offset(y = if (model.stateRoutes) (-15).dp else 0.dp)
                    .padding(14.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.me),
                contentDescription = "me",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        model.updateStateMe()
                    }
                    .offset(y = if (model.stateMe) (-15).dp else 0.dp)
                    .padding(14.dp)
            )
        }

    }

}
