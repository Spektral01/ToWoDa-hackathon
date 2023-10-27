package com.itsc.tuwoda.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.itsc.tuwoda.MyFloatingActionButton
import com.itsc.tuwoda.R

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MyBottomSheetScaffold(
    scaffoldState: BottomSheetScaffoldState,
    onState: (String) -> Unit,
    state: String,
    title: String
    ) {
    BottomSheetScaffold(
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 265.dp,
        sheetContent = {
            Scaffold(
                modifier = Modifier.fillMaxHeight(0.9f),
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = colorResource(id = R.color.blue_main)
                        ),
                        title = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.divider),
                                    contentDescription = "divider",
                                    modifier = Modifier
                                        .width(45.dp),
                                    tint = Color.White,
                                )
                                Text(
                                    text = title,
                                    color = Color.White,
                                    modifier = Modifier.padding(top = 5.dp),

                                )
                            }
                        }
                    )
                },
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 10.dp
                        )
                    ){
                        MyFloatingActionButton(
                            background = R.drawable.ellipsefull,
                            icon = R.drawable.ellipsefull,
                            size = 30.dp,
                        )
                        Card(
                            shape = RoundedCornerShape(45.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = colorResource(id = R.color.blue_main)
                            ),
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            androidx.compose.material3.TextField(
                                value = state,
                                onValueChange = {text ->
                                    onState(text)
                                },
                                modifier = Modifier.padding(horizontal = 25.dp, vertical = 10.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    textColor = Color.White,
                                    focusedIndicatorColor = Color.White,
                                    unfocusedIndicatorColor = Color.White,
                                )
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                MyFloatingActionButton(
                                    background = R.drawable.ellipsefull,
                                    icon = R.drawable.edit,
                                    size = 30.dp,
                                    colorBackground = R.color.blue_light_main_alfa
                                )
                                MyFloatingActionButton(
                                    background = R.drawable.ellipsefull,
                                    icon = R.drawable.trash,
                                    size = 30.dp,
                                    colorBackground = R.color.blue_light_main_alfa
                                )
                                MyFloatingActionButton(
                                    background = R.drawable.ellipsefull,
                                    icon = R.drawable.downdrop,
                                    size = 30.dp,
                                    colorBackground = R.color.blue_light_main_alfa

                                )
                            }
                        }
                    }
                }
            }



        },
        sheetShape = RoundedCornerShape(45.dp),
        content = {}
    )
}