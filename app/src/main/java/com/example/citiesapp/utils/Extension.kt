package com.example.citiesapp.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.*
import com.example.citiesapp.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.showToastMessage(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showAlertDialog(title: String, message: String, buttonOkText: String,buttonTextSecond: String?,buttonAction:()->Unit,buttonActionSecond:()->Unit) {
    val alertDialogBuilder = AlertDialog.Builder(this)
    alertDialogBuilder
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(buttonOkText) { dialog, _ ->
            buttonAction()
            dialog.dismiss()
        }.setNegativeButton(buttonTextSecond){dialog,_ ->
            buttonActionSecond()
            dialog.dismiss()
        }.show()
}

fun Context.showError(errorCode: String, message: String) {
    if (errorCode == "1002") {
        showAlertDialog(getString(R.string.error_no_network),message,getString(R.string.ok),null,{},{})
    } else {
       showAlertDialog(errorCode,message,getString(R.string.ok),null,{},{})
    }
}
