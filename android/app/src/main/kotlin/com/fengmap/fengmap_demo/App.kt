package com.fengmap.fengmap_demo

import android.app.Application
import com.fengmap.android.FMMapSDK
import io.flutter.app.FlutterApplication

class App : FlutterApplication() {
    override fun onCreate() {
        super.onCreate()
        FMMapSDK.init(this)
    }
}