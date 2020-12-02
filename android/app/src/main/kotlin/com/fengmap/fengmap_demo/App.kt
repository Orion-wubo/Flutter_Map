package com.fengmap.fengmap_demo

import com.fengmap.android.FMMapSDK
import com.fengmap.android.utils.FMLog
import io.flutter.app.FlutterApplication

class App : FlutterApplication() {
    override fun onCreate() {
        super.onCreate()
        FMLog.FMLOG_SDK_TEST_OPEN = true
        FMLog.FMLOG_DEBUG_OPEN = true
        FMMapSDK.init(this)
    }
}