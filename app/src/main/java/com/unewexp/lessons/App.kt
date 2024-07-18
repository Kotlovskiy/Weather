package com.unewexp.lessons;

import android.app.Application;

class App: Application() {
    lateinit var appGraph: AppGraph

    override fun onCreate() {
        super.onCreate()
        appGraph = DaggerAppGraph.create()
    }
}
