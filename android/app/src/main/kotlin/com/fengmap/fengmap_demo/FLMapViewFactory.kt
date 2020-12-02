package com.fengmap.fengmap_demo

import android.content.Context
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class FLMapViewFactory : PlatformViewFactory {

    private lateinit var messenger: BinaryMessenger

    constructor(binaryMessenger: BinaryMessenger) : super(StandardMessageCodec.INSTANCE) {
        this.messenger = binaryMessenger
    }
    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        val params = args as Map<String, Any>
        return FLMapView(context, messenger, viewId, params)
    }
}