package com.runew.fasthome4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    val layoutId = R.layout.activity_main
    fun getFragment() = DevicesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ASD","MainActivity onCreate")
        setContentView(layoutId)
        var fragment = supportFragmentManager.findFragmentById(R.id.main)
        if (fragment == null) {
            fragment = getFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.main, fragment)
                .commit()
        }

    }
}