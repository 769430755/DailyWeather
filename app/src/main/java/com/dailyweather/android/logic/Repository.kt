package com.dailyweather.android.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.dailyweather.android.logic.model.Place
import com.dailyweather.android.logic.network.DailyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

/**
 *  仓库类 -- 判断调用方请求的数据应该从本地还是从网络中获取
 */
object Repository {

    // todo liveData的使用？
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = DailyWeatherNetwork.searchPlaces(query)
            // todo kotlin的字符串可以直接用 == 来比较内容？
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Log.d("network", places.toString())
                Result.success(places)
            } else {
                Result.failure(RuntimeException("Response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }


}