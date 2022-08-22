package com.runew.fasthome4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    //фрагмент DevicesFragment
    lateinit var deviceFragment : DevicesFragment
    //создание activity,...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //...идентификация слоя с фрагментом и...
        var fragment = supportFragmentManager.findFragmentById(R.id.main)
        if (fragment == null) {
            fragment = DevicesFragment()
            //...инициализация DevicesFragment
            deviceFragment = fragment
            //Запуск фрагмента
            supportFragmentManager.beginTransaction()
                .add(R.id.main, fragment!!)
                .commit()
        }
    }
    //Нажатие кнопки "Удалить" - удаление устройства по id
    fun dialogClicked(itemId: Int){
        deviceFragment.deleteDevice(itemId)
    }
}