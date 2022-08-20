package com.runew.fasthome4

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.runew.fasthome4.ViewModelDevices.LifeCycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_device.*
import kotlinx.android.synthetic.main.activity_main.*

class DevicesFragment : Fragment(){
    val layoutId = R.layout.activity_device

    private lateinit var model: ViewModelDevices
//    private val adapter = DeviceAdapter<Devices>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ASD","DevicesFragment onCreate")
        model = ViewModelProvider(this).get(ViewModelDevices::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        Log.d("ASD","DevicesFragment onCreateView")
        return inflater.inflate(layoutId, container, false)
    }
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("ASD","DevicesFragment onActivityCreated")
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = model.adapter

        checkLifeCycle()
    }

    fun checkLifeCycle() {
        when (model.lifeCycle) {
            CREATED -> {
                Log.d("ASD", "CREATED")
                showProgress()
//                model.setListener { checkData() }
                model.loading(this)
            }
            PROGRESS -> {
//                showProgress()
//                model.setListener { checkData() }
            }

            ERROR -> {
//                showError(R.string.unable_to_load) {
//                    showProgress()
//                    model.setListener { checkData() }
//                    model.load()
//                }
            }

            LOADING -> {
                Log.d("ASD", "LOADING")
//                adapter.addItems(model.data.data)
                model.load()
                showContent()
//
//                adapter.setOnclickListener { category ->
//                    val intent = Intent(context, GalleryActivity::class.java)
//                    intent.putExtra("category", category)
//                    startActivity(intent)
//            }
            }

            LOADED -> {
                Log.d("ASD","LOADED")
//                adapter.addItems(model.data.data)
//                showContent()
//
//                adapter.setOnclickListener { category ->
//                    val intent = Intent(context, GalleryActivity::class.java)
//                    intent.putExtra("category", category)
//                    startActivity(intent)
//                }

            }
        }
    }
    fun showProgress() {
        recycler_view.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
//        error_group.visibility = View.GONE
    }
    fun showContent() {
        recycler_view.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
//        error_group.visibility = View.GONE
    }
}