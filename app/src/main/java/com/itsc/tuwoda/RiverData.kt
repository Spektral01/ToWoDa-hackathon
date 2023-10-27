package com.itsc.tuwoda

import com.yandex.mapkit.geometry.Point

data class RiverData(
    val id:Int,
    val name:String? = null,
    val width:Double? = null,
    val depth:Double? = null,
    val bridge:Double? = null,
    val distance:Double? = null,
    val layer:Int? = null,
    val rgeometry:List<Point>
)
