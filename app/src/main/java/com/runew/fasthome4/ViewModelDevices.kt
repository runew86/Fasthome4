package com.runew.fasthome4

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import com.runew.fasthome4.baseUrl

class ViewModelDevices : ViewModel() {
    enum class LifeCycle { CREATED, LOADING, LOADED, ERROR }
    var lifeCycle = LifeCycle.CREATED
    lateinit var data: DeviceList
    val adapter = DeviceAdapter<Devices>()
    lateinit var errorText: String

//    val baseUrl = baseUrl
    val api = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)
    //загрузка контента
    fun loading(fragment: DevicesFragment){
        //цикл фрагемнта - создание
        lifeCycle = LifeCycle.CREATED

        api.getData().enqueue(object : Callback<DeviceList> {
            override fun onResponse(call: Call<DeviceList>, response: Response<DeviceList>) {
                data = response.body()!!
                lifeCycle = LifeCycle.LOADING
                 adapter.addItems(data.data)
                fragment.checkLifeCycle()
//                loadListener()
            }
            override fun onFailure(call: Call<DeviceList>, t: Throwable?) {
                lifeCycle = LifeCycle.ERROR
                errorText = t!!.cause.toString()
                fragment.checkLifeCycle(errorText)
//                loadListener()
            }
        })

    }
    fun load()
    {
        lifeCycle = LifeCycle.LOADED
    }
    fun delete(itemId: Int){
        adapter.deleteItem(itemId)
    }
}