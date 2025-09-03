package com.rahulpatel.newsapp.utils.logger

//Why Use an Interface Here?
//Flexibility → You can swap out the logging mechanism without changing business logic.
//Dependency Injection → Pass a Logger into classes instead of hardcoding println().
//Testability → In unit tests, you can provide a FakeLogger to capture logs instead of printing.
interface Logger {
    fun d(tag: String, msg: String)
}