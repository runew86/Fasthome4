package com.runew.fasthome4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    //фрагмент DevicesFragment
    lateinit var deviceFragment : DevicesFragment
    //создание activity,...
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ASD","ON")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //...идентификация слоя с фрагментом и...
        var fragment = supportFragmentManager.findFragmentById(R.id.main)
        //если фрагмент не создан
        if (fragment == null) {
            //создаем фрагмент DevicesFragment
            fragment = DevicesFragment()
            //связываем переменную deviceFragment главной активити с созданным фрагментом
            deviceFragment = fragment
            //Запуск фрагмента
            supportFragmentManager.beginTransaction()
                .add(R.id.main, fragment!!)
                .commit()
        }
        else {
            //связываем переменную deviceFragment главной активити с ранее созданным фрагментом
            deviceFragment = fragment as DevicesFragment
            //Запуск фрагмента
            supportFragmentManager.beginTransaction()
                .commit()
        }
    }
    //Нажатие кнопки "Удалить" - удаление устройства по id
    fun dialogClicked(itemId: Int){
        //запуск функции удаления во фрагменте
        deviceFragment.deleteDevice(itemId)
    }
}