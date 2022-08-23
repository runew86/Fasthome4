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
        //если фрагмент не создан
        if (fragment == null) {
            //создаем фрагмент DevicesFragment
            fragment = DevicesFragment()
            //связываем переменную deviceFragment главной активити с созданным фрагментом
            deviceFragment = fragment
            //добавление и запуск фрагмента
            supportFragmentManager.beginTransaction()
                .add(R.id.main, fragment!!)
                .commit()
        }
        else {
            //связываем переменную deviceFragment главной активити с ранее созданным фрагментом
            deviceFragment = fragment as DevicesFragment
            //запуск фрагмента
            supportFragmentManager.beginTransaction()
                .commit()
        }
    }
    //Нажатие кнопки "Удалить" фрагмента AlertDialog - удаление устройства по id
    fun dialogClicked(itemId: Int){
        //вызов функции удаления во фрагменте
        deviceFragment.deleteDevice(itemId)
    }
}