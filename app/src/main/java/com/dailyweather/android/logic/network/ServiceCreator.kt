package com.dailyweather.android.logic.network

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

    /**
     * 1. infix 函数 ： 一个kotlin的语法糖（与上面的函数无关）
     * 2. 泛型和委托
     *      泛型：使程序可以不指定具体的类型而运行
     *      1） 泛型类
     *      2） 泛型方法
     *      泛型上界默认是Any？ ，所以是可空的，若要指定非空，使其上界变更为Any即可
     *      可通过指定泛型的上界来约束泛型的类型范围
     */

}