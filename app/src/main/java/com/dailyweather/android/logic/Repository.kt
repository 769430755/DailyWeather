package com.dailyweather.android.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.dailyweather.android.logic.model.Place
import com.dailyweather.android.logic.model.Weather
import com.dailyweather.android.logic.network.DailyWeatherNetwork
import com.dailyweather.android.logic.network.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/**
 *  仓库类 -- 判断调用方请求的数据应该从本地还是从网络中获取
 */
object Repository {

    // todo liveData的使用？
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = DailyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Log.d("network", places.toString())
            Result.success(places)
        } else {
            Result.failure(RuntimeException("Response status is ${placeResponse.status}"))
        }
    }


    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async { DailyWeatherNetwork.getRealtimeWeather(lng, lat) }
            val deferredDaily = async { DailyWeatherNetwork.getDailyWeather(lng, lat) }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(RuntimeException("realtime response is ${realtimeResponse.status}" +
                    "daily response is ${dailyResponse.status}"))
            }
        }
    }

    /**
     * block: suspend() 代指一个挂起的方法
     * block: suspend() suspend关键字表示所有传入的Lambda表达式中的代码也是拥有挂起函数上下文的
     * block: suspend() -> Result<T> 该函数返回一个Result<T>的对象
     * 封装的高阶函数
     */
    private fun <T> fire(context: CoroutineContext, block: suspend() -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}