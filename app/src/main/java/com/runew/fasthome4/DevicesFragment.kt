package com.runew.fasthome4

import android.content.Intent
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
        //... привязка адаптера фрагмента к адаптеру в моделе
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
        //привязка адаптера recycler_view с адаптером в моделе для хранения значения при обновлении главной активности
        recycler_view.adapter = adapter
        //событие нажатия иконки "Обновить" в тулбаре - перезапуск главной активности приложения
        update_view.setOnClickListener(){update()}
        //событие нажатия иконки "Обновить" при ошибке - перезапуск главной активности приложения
        error_update_view.setOnClickListener(){update()}
        //проверка жизнинного цикла фрагмента
        checkLifeCycle()
    }
    //функция перезапуска приложения
    fun update(){
        //создание интента на главную активность с флагом новой задачи
        val intent = Intent(this.context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //запуск/перезапуск главной активности (приложения)
        this.startActivity(intent)
    }
    //функция проверки жизненного цикла фрагмента (без аргументов)
    fun checkLifeCycle() {
        checkLifeCycle("")
    }
    //функция проверки жизненного цикла фрагмента (с аргументом)
    fun checkLifeCycle(arg: String) {
        when (model.lifeCycle) {
            //цикл создание фрагмента
            CREATED -> {
                //показать прогрессбар
                showProgress()
                //загрузка контента
                model.loading(this)
            }
            //ошибка загрузки контента
            ERROR -> {
                //показать экран ошибки с выводом аргумента - текста ошибки
                showError(arg)
            }
            //цикл - первая загрузка контента
            LOADING -> {
                //контент загружен
                model.load()
                //показать экран первой загрузки
                showContent()
            }
            //цикл - контент ранее загружен
            LOADED -> {
                //выгрузить загруженный контент
                showContent()
            }
        }
    }
    //показать прогрессбар
    //виден тулбар
    //виден прогрессбар
    fun showProgress() {
        layout_toolbar.visibility = View.VISIBLE
        layout_error.visibility = View.GONE
        recycler_view.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }
    //показать контент
    //виден тулбар
    //виден контент - список устройств
    fun showContent() {
        layout_toolbar.visibility = View.VISIBLE
        layout_error.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
    //показать ошибку
    //виден экран ошибки с текстом ошибки
    fun showError(exception: String) {
        layout_toolbar.visibility = View.GONE
        layout_error.visibility = View.VISIBLE
        tv_error_text.text = exception
        recycler_view.visibility = View.GONE
        progressBar.visibility = View.GONE
    }
    //функция вызывается из главной активности после нажатия кнопки "Удалить" фрагмента AlertDialog
    fun deleteDevice(itemId: Int){
        //вызов функции удаления устройства в моделе
        model.delete(itemId)
    }
}