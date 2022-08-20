package com.runew.fasthome4

import android.util.Log
import androidx.fragment.app.Fragment
import com.runew.fasthome4.Api
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModelDevices : ViewModel() {
    enum class LifeCycle { CREATED, PROGRESS, LOADING, LOADED, ERROR }
    var lifeCycle = LifeCycle.CREATED
    lateinit var data: DeviceList
    val adapter = DeviceAdapter<Devices>()

    val baseUrl = "https://api.fasthome.io"
    val api = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    fun loading(fragment: DevicesFragment){
        Log.d("ASD","ViewModelDevices.loading")
        lifeCycle = LifeCycle.PROGRESS
        api.getData().enqueue(object : Callback<DeviceList> {
            override fun onResponse(call: Call<DeviceList>, response: Response<DeviceList>) {
                data = response.body()!!
                lifeCycle = LifeCycle.LOADING
                Log.d("ASD","onResponse")
                adapter.addItems(data.data)
                fragment.checkLifeCycle()
//                loadListener()
            }
            override fun onFailure(call: Call<DeviceList>, t: Throwable?) {
                lifeCycle = LifeCycle.ERROR
                fragment.checkLifeCycle()
//                loadListener()
            }
        })

    }
    fun load()
    {
        lifeCycle = LifeCycle.LOADED
    }
}