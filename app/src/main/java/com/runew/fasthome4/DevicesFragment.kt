package com.runew.fasthome4

import android.content.Intent
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

class DevicesFragment : Fragment(){
    //model - ViewModelDevices - ViewModel
    private lateinit var model: ViewModelDevices
    //adapter - DeviceAdapter - RecyclerView.Adapter
    lateinit var adapter: DeviceAdapter<Devices>
    //создание фрагмента,...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //... привзяка модели к фрагменту,...
        model = ViewModelProvider(this).get(ViewModelDevices::class.java)
        //... привязка адаптера фрагмента к адавптеру в моделе
        adapter = model.adapter
    }
    //загрузка фрагмента на экран
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        //загружаем файл разметки и добавляем его в контейнер main
        return inflater.inflate(R.layout.activity_device, container, false)
    }
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    //фрагмент загружен
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //делаем список recycler_view линейным
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter
        update_view.setOnClickListener(){update()}
        error_update_view.setOnClickListener(){update()}
        checkLifeCycle()
    }
    fun update(){
        val intent = Intent(this.context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    }
    fun checkLifeCycle() {
        checkLifeCycle("")
    }
    fun checkLifeCycle(arg: String) {
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
                Log.d("ASD", "ERROR")
                if(arg!="")
                    showError(arg)
                else model.loading(this)
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
                showContent()

//                adapter.setOnclickListener { category ->
//                    val intent = Intent(context, GalleryActivity::class.java)
//                    intent.putExtra("category", category)
//                    startActivity(intent)
//                }

            }
        }
    }
    fun showProgress() {
        layout_toolbar.visibility = View.VISIBLE
        layout_error.visibility = View.GONE
        recycler_view.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }
    fun showContent() {
        layout_toolbar.visibility = View.VISIBLE
        layout_error.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
    fun showError(exception: String) {
        layout_toolbar.visibility = View.GONE
        layout_error.visibility = View.VISIBLE
        tv_error_text.text = exception
        recycler_view.visibility = View.GONE
        progressBar.visibility = View.GONE
    }
    fun deleteDevice(itemId: Int){
        Log.d("ASD",itemId.toString())
        model.delete(itemId)
    }
}