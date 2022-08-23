package com.runew.fasthome4

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DeleteDialog(device: Devices, itemId : Int) : DialogFragment() {
    var deviceName = device.name
    var deviceId = device.id.toInt()
    var itemId = itemId
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Удаление устройства")
                .setMessage("Вы хотите удалить $deviceName #$deviceId")
                .setCancelable(true)
                .setPositiveButton("Удалить",
                    DialogInterface.OnClickListener { dialog, id ->
                        (activity as MainActivity)?.dialogClicked(itemId)
                })
                .setNegativeButton("Отмена",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}