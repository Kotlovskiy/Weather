package com.unewexp.lessons;

import android.app.Application;
import android.content.Context

class App: Application() {
    lateinit var appGraph: AppGraph

    override fun onCreate() {
        super.onCreate()
        appGraph = DaggerAppGraph.create()
    }
}

val Context.appGraph: AppGraph
    get() = when(this) {
        is App -> appGraph
        else -> this.applicationContext.appGraph
    }
