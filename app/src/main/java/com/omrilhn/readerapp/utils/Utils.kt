package com.omrilhn.readerapp.utils

import android.content.Context
import android.widget.Toast
import com.google.firebase.Timestamp
import java.text.DateFormat

fun formatDate(timestamp: Timestamp): String {
    return DateFormat.getDateInstance()
        .format(timestamp.toDate())
        .toString().split(",")[0]
}

fun showToast(context: Context, msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_LONG)
            .show()
}