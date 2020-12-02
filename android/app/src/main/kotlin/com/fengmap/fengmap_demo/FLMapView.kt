package com.fengmap.fengmap_demo

import android.content.Context
import android.util.Log
import android.view.View
import com.fengmap.android.map.FMMap
import com.fengmap.android.map.FMMapView
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView

class FLMapView : PlatformView, MethodChannel.MethodCallHandler {
    lateinit var fmMapView: FMMapView
    lateinit var map: FMMap
    private var autoUpdate = true
    var NATIVE_CCTV_VIEW_TYPE_ID = "com.fengmap.fengmap_demo/flmapview" //原生控件对应的viewtypeid

    constructor(context: Context, binaryMessenger: BinaryMessenger, id: Int, params: Map<String, Any>) {
        val methodChannel = MethodChannel(binaryMessenger, NATIVE_CCTV_VIEW_TYPE_ID);
        methodChannel.setMethodCallHandler(this);
        fmMapView = FMMapView(context)
        map = fmMapView.fmMap
        if (params.containsKey("mapId")) {
            val mapId: String = params["mapId"] as String
            autoUpdate = params["autoUpdate"] as Boolean
            map.openMapById(mapId, autoUpdate)
        }
    }


    override fun getView(): View {
        return fmMapView
    }

    override fun dispose() {

    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        Log.i("FLMapView onMethodCall",
                "FLMapView MethodChannel call.method:"
                        + call.method.toString()
                        + "  call arguments:"
                        + call.arguments)
        if ("setFloorId" == call.method) {
            val floorId = call.argument("floorId") as Int?
            map.setFocusByGroupId(floorId!!, null)

            val dataMap = mapOf("floorId" to floorId)
            result.success(dataMap)
        }
    }
}