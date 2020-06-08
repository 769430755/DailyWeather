package com.dailyweather.android.logic.network

import com.dailyweather.android.MyApplication
import com.dailyweather.android.logic.model.DailyResponse
import com.dailyweather.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    // 获取实时的天气信息
    @GET("v2.5/${MyApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    // 获取未来的天气信息
    @GET("v2.5/${MyApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>
}