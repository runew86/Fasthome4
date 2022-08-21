package com.runew.fasthome4

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DeleteDialog() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выбор есть всегда")
                .setMessage("Выбери пищу")
                .setCancelable(true)
                .setPositiveButton("Удалить",
                    DialogInterface.OnClickListener { dialog, id ->
                    Log.d("ASD",activity.toString())
                })
                .setNegativeButton("Отмена",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}