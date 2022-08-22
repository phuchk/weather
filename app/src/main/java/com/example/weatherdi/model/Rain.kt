package com.example.weatherdi.model

import com.google.gson.annotations.SerializedName

//Serialize name
data class Rain(
    @SerializedName("1h")
    val _1h: Float
) {}