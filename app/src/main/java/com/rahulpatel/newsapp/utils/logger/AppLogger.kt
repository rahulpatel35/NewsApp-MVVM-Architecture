package com.rahulpatel.newsapp.utils.logger

import android.util.Log

//Your AppLogger is a production logger that wraps Android’s Log.d.
//Because it implements a Logger interface, you can swap implementations
// (fake logger, file logger, crash logger) without changing the rest of your app.

class AppLogger : Logger {
    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }
}