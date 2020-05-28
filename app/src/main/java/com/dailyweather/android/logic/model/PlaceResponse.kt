package com.dailyweather.android.logic.model

import android.location.Address
import android.location.Location
import com.google.gson.annotations.SerializedName

// 实体类
data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(val name: String, val location: Location,
                 @SerializedName("formatted_address") val address: Address)

data class Location(val lng: String, val lat: String)