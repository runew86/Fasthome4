package com.runew.fasthome4

import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModelDevices : ViewModel() {
    //перечисление жизненных циклов модели -> фрагмента
    enum class LifeCycle { CREATED, LOADING, LOADED, ERROR }
    //переменная lifeCycle при инициализации хранит значение - создание модели
    var lifeCycle = LifeCycle.CREATED
    //переменная списка устройств
    lateinit var data: DeviceList
    //адаптер recycler_view модели - адаптер списка устройств
    val adapter = DeviceAdapter<Devices>()
    //текст ошибки при исключении загрузки
    lateinit var errorText: String
    //построение связи клиент-сервер с помощью Retrofit2...
    // ...на основе интерфейса вызова списка устройств с сервера
    val api = Retrofit.Builder()
        //вызов по адресу baseUrl
        .baseUrl(baseUrl)
        //создание фабрики конвертации для GSON
        .addConverterFactory(GsonConverterFactory.create())
        //построение связи с сервером
        .build()
        //создание реализации Api
        .create(Api::class.java)
    //загрузка контента
    fun loading(fragment: DevicesFragment){
        //цикл фрагмента - создание
        lifeCycle = LifeCycle.CREATED
        //создание асинхронного вызова списка устройств с сервера
        api.getData().enqueue(object : Callback<DeviceList> {
            override fun onResponse(call: Call<DeviceList>, response: Response<DeviceList>) {
                data = response.body()!!
                lifeCycle = LifeCycle.LOADING
                adapter.addItems(data.data)
                fragment.checkLifeCycle()
            }
            override fun onFailure(call: Call<DeviceList>, t: Throwable?) {
                lifeCycle = LifeCycle.ERROR
                errorText = t!!.cause.toString()
                fragment.checkLifeCycle(errorText)
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