package com.rahulpatel.newsapp.utils

/*
TestNetworkHelper fakes network availability so tests behave as if internet is always ON.
Why it’s used:
In unit tests:
Simulates always-connected internet
Avoids dependency on real device/network state
Makes tests predictable*/

class TestNetworkHelper : NetworkHelper {
    override fun isNetworkConnected(): Boolean {
        return true
    }
}