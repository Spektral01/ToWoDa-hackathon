package com.example.test.ui
import android.content.Context
import android.graphics.PointF
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.itsc.tuwoda.R
import com.itsc.tuwoda.RiverData
import com.yandex.mapkit.Animation
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

class MapViewModel(
    var mapView: MapView,
    var context: Context
): ViewModel() {

    private var tempPlacemark:PlacemarkMapObject? = null
    private var placemarks:MutableList<PlacemarkMapObject> = mutableListOf()
    private var infoPlacemarks:MutableList<PlacemarkMapObject> = mutableListOf()
    private var imageProviders: kotlin.collections.Map<String, ImageProvider> = mapOf(
        "dollar" to ImageProvider.fromResource(context, R.drawable.ic_pin),
        "start_end" to ImageProvider.fromResource(context,R.drawable.ic_start_end),
        //"my_geo" to ImageProvider.fromResource(context,R.drawable.ic_people)
    )
    var myGeolocationPlacemark: PlacemarkMapObject? = null

    private var cameraListener:CameraListener? = null
    private var roadRiverListener:InputListener? = null
    private var addNewPlacemarkState:Boolean = false


    var tapPoint:List<Pair<Double,Double>>? = null
    var tapRiver:List<Pair<Double,Double>>? = null
    fun setMyLocation(point: Point){
        myGeolocationPlacemark = mapView.map.mapObjects.addPlacemark(
            point,
            imageProviders["start_end"]!!
        )
    }

    val testInput:RiverRoadsInput = RiverRoadsInput(
        start = Point(56.616055, 84.767233),
        end = Point(56.582062, 84.902435),
        s = "sudno"
    )

    val testRoad = listOf(
        RiverData(
            id = 0,
            name = "first river",
            depth = 0.3,
            rgeometry = listOf(
                Point(56.616055, 84.767233),
                Point(56.616055, 84.767233),
                Point(56.599316, 84.780109),
                Point(56.594242, 84.796664),
                Point(56.590183, 84.822417),
            )
        ),
        RiverData(
            id = 1,
            depth = 0.7,
            name = "p river",
            rgeometry = listOf(
                Point(56.590183, 84.822417),
                Point(56.594242, 84.844491),
                Point(56.597287, 84.867485),
                Point(56.595764, 84.884960),
            )
        ),
        RiverData(
            id = 2,
            depth = 1.1,
            name = "second river",
            width = 34.5,
            rgeometry = listOf(
                Point(56.595764, 84.884960),
                Point(56.582062, 84.902435),
            )
        ),
    )
    data class RiverRoadsInput(
        var start:Point,
        var end:Point,
        var s:String
    )

    //region Algorithm

    var mapStartX:kotlin.collections.Map<Double, Int> = mapOf()
    var mapStartY:kotlin.collections.Map<Double, Int> = mapOf()
    var mapEndX:kotlin.collections.Map<Double, Int> = mapOf()
    var mapEndY:kotlin.collections.Map<Double, Int> = mapOf()
    var table :kotlin.collections.Map<Double, Double> = mapOf()

    var data:List<List<Pair<Double, Double>>> = listOf(
        listOf(
            Pair(56.616055, 84.767233),
            Pair(56.616055, 84.767233),
            Pair(56.599316, 84.780109),
            Pair(56.594242, 84.796664),
            Pair(56.590183, 84.822417),
        ),
        listOf(
            Pair(56.590183, 84.822417),
            Pair(56.594242, 84.844491),
            Pair(56.597287, 84.867485),
            Pair(56.595764, 84.884960),
        ),
        listOf(
            Pair(56.595764, 84.884960),
            Pair(56.582062, 84.902435),
        ),
    )
    fun initMaps(){

    }

    //endregion

    fun goToMyLocation(){
        mapView.map.apply {
            Toast.makeText(
                context,
                "${myGeolocationPlacemark?.geometry?.latitude}",
                Toast.LENGTH_LONG
            ).show()
            myGeolocationPlacemark?.let {
                move(
                    CameraPosition(
                        it.geometry,
                        cameraPosition.zoom,
                        cameraPosition.azimuth,
                        cameraPosition.tilt),
                    Animation(Animation.Type.SMOOTH, 30f),
                    null
                )
            }
        }
    }

    private fun getColorRiver(d:Double?):Int{
        if (d != null) {
            when{
                ((d>=0) && (d < 0.3)) -> {
                    return R.color.r1
                }
                ((d>=0.3) && (d < 0.6)) -> {
                    return R.color.r2
                }
                ((d>=0.6) && (d < 0.9)) -> {
                    return R.color.r3
                }
                ((d>=0.9) && (d < 1.2)) -> {
                    return R.color.r4
                }
            }
        }
        return R.color.gray

    }

    private fun DriwerRoad(l:List<Point>){
        mapView.map.mapObjects.addPolyline(
            Polyline(
                l
            )
        )
    }


    fun addRoute(){
        val l:MutableList<Point> = mutableListOf()
        placemarks.forEach {
            l.add(it.geometry)
        }
        DriwerRoad(l)

    }

    fun goToStateSearchingPointCraph(grahp:List<List<Pair<Double,Double>>>){
        roadRiverListener = object : InputListener {
            override fun onMapTap(map: Map, point: Point) {
                var minD:Double = 9999999.0
                for(part in grahp){
                    for(p in part) {
                        val newMinDist = abs(min(
                            minD,
                            sqrt(
                                (abs(p.second-point.longitude)).pow(2)
                                        +
                                        (abs(p.first-point.latitude)).pow(2)
                            ))
                        )
                        if(minD != newMinDist){
                            minD = newMinDist
                            tapPoint = listOf(p)
                            tapRiver = part
                            Toast.makeText(
                                context,
                                "${tapPoint!![0].first} : ${tapPoint!![0].second}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }

            override fun onMapLongTap(map: Map, point: Point) {
                // Handle long tap ...
            }
        }
        mapView.map.addInputListener(roadRiverListener!!)
    }
    fun drawRiverRoad(road:List<RiverData>) {
        road.forEach {
            val river = mapView.map.mapObjects.addPolyline(
                Polyline(it.rgeometry!!)
            )
            river.apply {
                strokeWidth = 5f
                setStrokeColor(ContextCompat.getColor(context, getColorRiver(it.depth)))
                outlineWidth = 1f
                outlineColor = ContextCompat.getColor(context, R.color.black)
            }

            val infoPoint = it.rgeometry[it.rgeometry.size / 2]
            val infoPlacemarkMapObject = mapView.map.mapObjects.addPlacemark(
                infoPoint
            )
            //region val infoText
            val infoText = (
                    "" + if (it.name != null) { "name: ${it.name} \n" } else ""
                            + if (it.width != null) { "width: ${it.width} \n" } else ""
                            + if (it.depth != null) { "depth: ${it.depth} \n" } else ""
                            + if (it.bridge != null) { "bridge: ${it.bridge} \n" } else ""
                            + if (it.distance != null) { "distance: ${it.distance} \n" } else ""
                    )
            //endregion

            infoPlacemarkMapObject.useCompositeIcon().apply {
                setIcon(
                    "pin",
                    imageProviders["dollar"]!!,
                    IconStyle().apply {
                        anchor = PointF(0.5f, 1.0f)
                        scale = 0.9f
                    }
                )
            }
            /*
            roadRiverListener = object : InputListener {
                override fun onMapTap(map: Map, point: Point) {
                    var minD:Double = 9999999.0
                    var minDR:RiverData = road[0]
                    for(part in road){
                        for(p in part.rgeometry) {
                            val newMinDist = min(
                                minD,
                                sqrt(
                                (abs(p.longitude-point.longitude)).pow(2)
                                        +
                                    (abs(p.latitude-point.latitude)).pow(2)
                                )
                            )
                            if(minD != newMinDist){
                                minDR = part
                                minD = newMinDist
                            }
                        }
                    }
                    if(abs(minD) < 0.01){
                        infoPlacemarkMapObject.geometry = point
                        Toast.makeText(
                            context,
                            minDR.name.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onMapLongTap(map: Map, point: Point) {
                    // Handle long tap ...
                }
            }
            mapView.map.addInputListener(roadRiverListener!!)

             */

            infoPlacemarks.add(infoPlacemarkMapObject)
        }
    }

    private fun updateCenterPlacemar(imageProvider:ImageProvider){
        val centerX = mapView.width / 2f
        val centerY = mapView.height / 2f
        val centerPoint = ScreenPoint(centerX, centerY)
        val worldPoint = mapView.screenToWorld(centerPoint)
        tempPlacemark = mapView.map.mapObjects.addPlacemark(
            worldPoint, imageProvider
        )
    }
    fun goTOAddNewPlacemarkState(){
        addNewPlacemarkState = true
        val imageProvider = ImageProvider.fromResource(context,R.drawable.ic_pin)
        updateCenterPlacemar(imageProvider)

        cameraListener = object:CameraListener{
            override fun onCameraPositionChanged(
                p0: Map,
                p1: CameraPosition,
                p2: CameraUpdateReason,
                p3: Boolean
            ) {
                tempPlacemark?.let {
                    mapView.map.mapObjects.remove(it)
                }

                updateCenterPlacemar(imageProvider)
            }
        }
        cameraListener?.let {
            mapView.map.addCameraListener(it)
        }
    }

    fun goToBasicState(){
        addNewPlacemarkState = false
        tempPlacemark?.let {
            placemarks.add(it)
        }
        cameraListener?.let {
            mapView.map.removeCameraListener(it)
        }
        tempPlacemark = null
    }
}