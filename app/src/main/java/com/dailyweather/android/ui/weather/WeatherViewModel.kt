package com.dailyweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dailyweather.android.logic.Repository
import com.dailyweather.android.logic.model.Location

class WeatherViewModel: ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()

    // 这三个变量保证手机在发生屏幕旋转的时候不会丢失
    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveDouble = Transformations.switchMap(locationLiveData) {
        location -> Repository.refreshWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }


}