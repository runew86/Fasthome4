package com.runew.fasthome4

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class DeviceAdapter<T : Devices>: RecyclerView.Adapter<DeviceAdapter.ItemHolder>() {

    private val list = mutableListOf<T>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_device, parent, false)
        )
    }
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val device = list[position]
        holder.bind(device)
        val baseUrl = "https://api.fasthome.io"
        holder.loadImage("$baseUrl${device.icon}")
        holder.itemView.setOnClickListener {
            performOptionsMenuClick(holder.itemView,device)
        }
    }
    override fun getItemCount() = list.size
    fun addItems(items: List<T>) {
        this.list.addAll(items)
        Log.d("ASD",items.toString())
        notifyDataSetChanged()
    }
    private fun performOptionsMenuClick(itemView: View, device:Devices)//, list: MutableList<Devices>, itemView: View)
    {
        val popupMenu = PopupMenu(itemView.context,itemView)
        popupMenu.inflate(R.menu.options_menu)
        popupMenu.setOnMenuItemClickListener(object: PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.action_edit -> {

                    }
                    R.id.action_delete -> {
                        list.remove(device)
                        notifyDataSetChanged()
                    }

                }
                return false
            }
        })
        popupMenu.show()
    }
    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        //private val tv_id: TextView = view.findViewById(R.id.tv_id)
        private val tv_name: TextView = view.findViewById(R.id.tv_name)
        private val iv_icon: ImageView = view.findViewById(R.id.iv_icon)
        private var tv_isonline: TextView = view.findViewById(R.id.tv_isonline)
        private var tv_type: TextView = TextView(view.context)
        private var tv_status: TextView = view.findViewById(R.id.tv_status)
        private val iv_status: ImageView = view.findViewById(R.id.iv_status)
        //private var tv_lastworktime: TextView = view.findViewById(R.id.tv_lastWorkTime)


        fun bind(device: Devices) {
            //tv_id.text = device.id
            tv_name.text = device.name.uppercase()
            tv_isonline.text = isOnline(device.isOnline)
            tv_status.text = setStatus(device.type, device.status)
            //tv_lastworktime.text = device.lastWorkTime
        }
        fun loadImage(url: String) {
            GlideApp.with(itemView)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .into(iv_icon)
        }
        fun isOnline(online: String): String
        {
            if(online=="true")
            {
                tv_isonline.setTextColor(ContextCompat.getColor(itemView.context,R.color.acid_green))
                return "ON LINE"
            }
            else
            {
                tv_isonline.setTextColor(ContextCompat.getColor(itemView.context,R.color.acid_red))
                return "OFF LINE"
            }
        }
        fun setStatus(deviceType: String, deviceStatus: String):String
        {
            when(deviceType)
            {
                "1" -> {
                    if(deviceStatus=="Работает") iv_status.setImageDrawable(
                        AppCompatResources.getDrawable(
                            itemView.context,
                            R.drawable.lamp_work
                        )
                    )
                    if(deviceStatus=="Выключен") iv_status.setImageDrawable(
                        AppCompatResources.getDrawable(
                            itemView.context,
                            R.drawable.lamp_not_work
                        )
                    )
                    return deviceStatus.uppercase()
                }
                "2" -> {
                    if(deviceStatus=="1")
                    {
                        iv_status.setImageDrawable(
                            AppCompatResources.getDrawable(
                                itemView.context,
                                R.drawable.no_gas
                            )
                        )
                        return "ГАЗ НЕ ОБНАРУЖЕН"
                    }
                    if(deviceStatus=="2")
                    {
                        iv_status.setImageDrawable(
                            AppCompatResources.getDrawable(
                                itemView.context,
                                R.drawable.gas
                            )
                        )
                        return "ЗАГАЗОВАННОСТЬ"
                    }
                }
            }
            return "СТАТУС НЕ ОПРЕДЕЛЕН"
        }
    }
}