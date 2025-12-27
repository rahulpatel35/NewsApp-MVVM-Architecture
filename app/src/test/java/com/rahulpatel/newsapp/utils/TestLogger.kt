package com.rahulpatel.newsapp.utils

import com.rahulpatel.newsapp.utils.logger.Logger
/*
TestLogger is a no-op logger used to disable logging during tests.
Why it’s used:
Avoids real logging
Prevents crashes or unwanted output
Keeps tests clean and silent
*/
class TestLogger : Logger {
    override fun d(tag: String, msg: String) {

    }
}