package com.itsc.tuwoda

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.test.ui.MapViewModel
import com.itsc.tuwoda.ui.theme.MyBottomSheetScaffold
import com.itsc.tuwoda.ui.theme.MyFABWithText
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationManager
import com.yandex.mapkit.location.LocationStatus
import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val model = MyViewModel()

    private var mapViewModel: MapViewModel? = null

    private lateinit var context: Context
    private lateinit var locationManager: LocationManager
    private lateinit var pLauncher: ActivityResultLauncher<String>

    //region Permission func
    private fun registerPermissionListener(){
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                //mapViewModel?.goToMyLocation()
            }
            else{

            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun doItAndCheckPermissions(
        action:() -> Unit
    ){
        when{
            (ContextCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    &&
                    ContextCompat.checkSelfPermission(
                        this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                    &&
                    ContextCompat.checkSelfPermission(
                        this@MainActivity, Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                    &&
                    ContextCompat.checkSelfPermission(
                        this@MainActivity, Manifest.permission.INTERNET
                    ) == PackageManager.PERMISSION_GRANTED )-> {
                action()
            }
            else -> {
                pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                pLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                pLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                pLauncher.launch(Manifest.permission.INTERNET)
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("f3d7b1d9-51c9-46c4-9c3c-c3e25d30f0d8")
        MapKitFactory.initialize(this)
        locationManager = MapKitFactory.getInstance().createLocationManager()
        //region locationManager Setting

        locationManager.requestSingleUpdate(
            object: LocationListener {
                override fun onLocationUpdated(p0: Location) {
                    mapViewModel?.setMyLocation(
                        p0.position
                    )
                }

                override fun onLocationStatusUpdated(p0: LocationStatus) {
                    Toast(
                        context,

                        )
                }
            }
        )

        locationManager.subscribeForLocationUpdates(
            50.0,
            10,
            50.0,
            false,
            FilteringMode.ON,
            object: LocationListener {
                override fun onLocationUpdated(p0: Location) {
                    mapViewModel?.setMyLocation(
                        p0.position
                    )
                }

                override fun onLocationStatusUpdated(p0: LocationStatus) {
                    Toast.makeText(context,"123", Toast.LENGTH_LONG).show()
                }

            }
        )
        //endregion
        setContent {
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

            context = LocalContext.current
            mapViewModel = viewModel<MapViewModel>(
                factory = object : ViewModelProvider.Factory {
                    override fun<T: ViewModel> create(modelClass: Class<T>): T{
                        return MapViewModel(
                            mapView = MapView(context).apply{
                                this.map.move(
                                    CameraPosition(
                                        Point(
                                            56.452387,
                                            84.972267
                                        ),
                                        10.0f,
                                        0.0f,
                                        0.0f),
                                    Animation(Animation.Type.SMOOTH, 0f),
                                    null
                                )
                            },
                            context = context
                        ) as T
                    }
                }
            )

            Scaffold(
                floatingActionButton = {
                    if (model.stateMap){
                        Column(
                            modifier = Modifier.offset(y = 25.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Box(
                                contentAlignment = Alignment.TopEnd
                            ) {
                                if (model.stateRouting){
                                    MyFABWithText(
                                        text = "Построить новый маршрут",
                                        x = (-15).dp,
                                        y = (-45).dp,
                                        paddingCardHorizontal = 20.dp,
                                        paddingTextHorizontal = 10.dp,
                                        sizeButton = 45.dp,
                                        backgroundButton = R.drawable.ellipsefull,
                                        iconButton = R.drawable.plus,
                                        colorBackgroundButton = R.color.blue_light_main,
                                        colorBackgroundText = R.color.blue_light_main_alfa,
                                        model = model
                                    )
                                    if(model.stateMapDialog){
                                        AlertDialog(
                                            containerColor = colorResource(id = R.color.blue_main_alfa),
                                            onDismissRequest = {
                                                model.stateMapDialog = false
                                                model.stateTextTitle = ""
                                                model.stateTextName = ""
                                            },
                                            confirmButton = {
                                                Button(
                                                    onClick = { /*TODO*/ },
                                                    colors = ButtonDefaults.buttonColors(
                                                        containerColor = Color.White,
                                                        contentColor = Color.Black
                                                    )
                                                ) {
                                                    Text(text = "Построить")
                                                }
                                            },
                                            dismissButton = {
                                                Button(
                                                    onClick = {
                                                        model.stateMapDialog = false
                                                        model.stateTextTitle = ""
                                                        model.stateTextName = ""
                                                    },
                                                    colors = ButtonDefaults.buttonColors(
                                                        containerColor = Color.White,
                                                        contentColor = Color.Black
                                                    )
                                                ) {
                                                    Text(text = "Назад")
                                                }
                                            },
                                            text = {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    ItemDialog(
                                                        name = "Начальная точка",
                                                        colorName = Color.White,
                                                        state = model.stateTextName,
                                                        onState = {state ->
                                                            model.stateTextName = state
                                                        }
                                                    )
                                                    ItemDialog(
                                                        name = "Конечная точка",
                                                        colorName = Color.White,
                                                        state = model.stateTextTitle,
                                                        onState = {state ->
                                                            model.stateTextTitle = state
                                                        }
                                                    )
                                                    MyFloatingActionButton(
                                                        background = R.drawable.ellipsefull,
                                                        icon = R.drawable.plus,
                                                        scaleY = 15.dp,
                                                        colorBackground = R.color.white,
                                                        tint = colorResource(id = R.color.blue_main_alfa),
                                                    )
                                                }

                                            },
                                        )
                                    }
                                    MyFABWithText(
                                        backgroundButton = R.drawable.ellipsefull,
                                        iconButton = R.drawable.star,
                                        colorBackgroundButton = R.color.blue_light_main,
                                        colorBackgroundText = R.color.blue_light_main_alfa,
                                        text = "Повторить маршрут",
                                        sizeButton = 45.dp,
                                        x = (-65).dp,
                                        paddingCardHorizontal = 20.dp,
                                        paddingTextHorizontal = 10.dp,
                                    )
                                }
                                MyFloatingActionButton(
                                    background = R.drawable.ellipsefull,
                                    icon = R.drawable.routing,
                                    padding = 5.dp,
                                    state = model.stateRouting,
                                    onState = {state ->
                                        model.stateRouting = state
                                    }
                                )
                            }
                            MyFloatingActionButton(
                                background = R.drawable.ellipsefull,
                                icon = R.drawable.geo,
                                padding = 5.dp
                            )
                        }
                    }
                },
                floatingActionButtonPosition = FabPosition.End,
                content = {
                    YandexMap()
                    if (model.stateSailing){
                        MyBottomSheetScaffold(
                            scaffoldState = bottomSheetScaffoldState,
                            state = model.stateTextTitleSailing,
                            onState = { state ->
                                model.stateTextTitleSailing = state
                            },
                            title = "Флот"
                        )
                    }
                    if (model.stateRoutes){
                        BottomSheetScaffold(
                            backgroundColor = Color.Transparent,
                            contentColor = Color.Transparent,
                            scaffoldState = bottomSheetScaffoldState,
                            sheetPeekHeight = 400.dp,
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
                                                        text = "История маршрутов",
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
                                                Column {
                                                    TextField(
                                                        value = model.stateTextTitleRoutes,
                                                        onValueChange = { title ->
                                                            model.stateTextTitleRoutes = title
                                                        },
                                                        supportingText = {
                                                            Text(text = "Начальная точка")
                                                        },
                                                        modifier = Modifier.padding(15.dp),
                                                        colors = TextFieldDefaults.textFieldColors(
                                                            containerColor = Color.Transparent,
                                                            textColor = Color.White,
                                                            focusedIndicatorColor = Color.White,
                                                            unfocusedIndicatorColor = Color.White,
                                                            focusedSupportingTextColor = Color.White,
                                                            unfocusedSupportingTextColor = Color.White
                                                        )
                                                    )
                                                    TextField(
                                                        value = model.stateTextTitleRoutes1,
                                                        onValueChange = { title ->
                                                            model.stateTextTitleRoutes1 = title
                                                        },
                                                        supportingText = {
                                                            Text(text = "Конечная точка")
                                                        },
                                                        modifier = Modifier.padding(15.dp),
                                                        colors = TextFieldDefaults.textFieldColors(
                                                            containerColor = Color.Transparent,
                                                            textColor = Color.White,
                                                            focusedIndicatorColor = Color.White,
                                                            unfocusedIndicatorColor = Color.White,
                                                            focusedSupportingTextColor = Color.White,
                                                            unfocusedSupportingTextColor = Color.White
                                                        )
                                                    )
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 10.dp),
                                                    horizontalArrangement = Arrangement.SpaceAround
                                                ) {
                                                    MyFloatingActionButton(
                                                        background = R.drawable.ellipsefull,
                                                        icon = R.drawable.routing,
                                                        size = 50.dp,
                                                        colorBackground = R.color.blue_light_main_alfa,
                                                        onState = {
                                                            mapViewModel?.let{
                                                                it.drawRiverRoad(it.testRoad)
                                                            }
                                                        }
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
                    if (model.stateMe){
                        MyBottomSheetScaffold(
                            scaffoldState = bottomSheetScaffoldState,
                            state = model.stateTextTitleMe,
                            onState = { state ->
                                model.stateTextTitleMe = state
                            },
                            title = "Профиль"
                        )

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        contentAlignment = Alignment.TopEnd,
                    ){

                        FloatingActionButton(
                            onClick = { },
                            modifier = Modifier
                                .background(Color.Transparent)
                                .size(50.dp)
                                .paint(
                                    painter = painterResource(id = R.drawable.more),
                                    contentScale = ContentScale.Crop
                                ),
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent,
                            elevation = FloatingActionButtonDefaults.elevation(0.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.morepoint),
                                contentDescription = "moreVert",
                                modifier = Modifier
                                    .size(50.dp * 0.6f)
                                    .background(Color.Transparent),
                                tint = Color.White
                            )
                        }
                    }
                },
                bottomBar =  {
                    MyBottomBar(model = model)
                }
            )
        }
    }

    override fun onStop() {
        mapViewModel?.mapView?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        mapViewModel?.mapView?.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    @Composable
    fun YandexMap(
        modifier: Modifier = Modifier
    ) {
        AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = {
                mapViewModel!!.mapView
            },
            update = {
                mapViewModel!!.mapView
            }
        )
    }
}