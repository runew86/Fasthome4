package com.runew.fasthome4

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

//интерфейс вызова списка устройств с сервера
interface Api {
    @GET("/api/v1/test/devices")
    fun getData() : Call<DeviceList>
}