package com.itsc.tuwoda

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MyViewModel:ViewModel() {
    var stateMap by mutableStateOf(true)
    var stateRoutes by mutableStateOf(false)
    var stateMe by mutableStateOf(false)
    var stateSailing by mutableStateOf(false)

    var stateRouting by mutableStateOf(false)
    var stateMapDialog by mutableStateOf(false)

    var stateTextName by mutableStateOf("")
    var stateTextTitle by mutableStateOf("")
    var stateTextTitleSailing by mutableStateOf("")
    var stateTextTitleRoutes by mutableStateOf("56.616055, 84.767233")
    var stateTextTitleRoutes1 by mutableStateOf("56.582062, 84.902435")

    var stateTextTitleMe by mutableStateOf("")


    fun updateStateMap() {
        stateMap = true
        stateMe = false
        stateRoutes = false
        stateSailing = false
    }
    fun updateStateRoutes() {
        stateRoutes = true
        stateMe = false
        stateMap = false
        stateSailing = false
    }
    fun updateStateMe() {
        stateMe = true
        stateMap = false
        stateRoutes = false
        stateSailing = false
    }
    fun updateStateSailing() {
        stateSailing = true
        stateMe = false
        stateMap = false
        stateRoutes = false

    }
}