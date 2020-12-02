package com.fengmap.fengmap_demo

import android.content.Context
import android.util.Log
import android.view.View
import com.fengmap.android.FMErrorMsg
import com.fengmap.android.map.FMMap
import com.fengmap.android.map.FMMapUpgradeInfo
import com.fengmap.android.map.FMMapView
import com.fengmap.android.map.event.OnFMMapInitListener
import com.fengmap.android.map.event.OnFMMapThemeListener
import com.fengmap.android.utils.FMLog
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView

class FLMapView(context: Context, binaryMessenger: BinaryMessenger, id: Int, params: Map<String, Any>) : PlatformView,
        MethodChannel.MethodCallHandler, OnFMMapInitListener, OnFMMapThemeListener {
    private var methodChannel: MethodChannel
    private var fmMapView: FMMapView
    private var map: FMMap
    private var autoUpdate = true
    private var VIEW_TYPE_ID = "com.fengmap.fengmap_demo/flmapview" //原生控件对应的viewtypeid

    init {
        methodChannel = MethodChannel(binaryMessenger, VIEW_TYPE_ID)
        methodChannel.setMethodCallHandler(this)
        fmMapView = FMMapView(context)
        map = fmMapView.fmMap
        if (params.containsKey("mapId")) {
            val mapId: String = params["mapId"] as String
            autoUpdate = params["autoUpdate"] as Boolean

            map.setOnFMMapInitListener(this)
            map.onFMMapThemeListener = this
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

    override fun onMapInitSuccess(p0: String?) {
        methodChannel.invokeMethod("mapInitSuccess", true)
    }

    override fun onMapInitFailure(p0: String?, p1: Int) {
        FMLog.le("错误", FMErrorMsg.getErrorMsg(p1))
        val dataMap = mapOf("mapInitErrorInfo" to FMErrorMsg.getErrorMsg(p1))
        methodChannel.invokeMethod("mapInitFailure", dataMap)
    }

    override fun onUpgrade(p0: FMMapUpgradeInfo?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSuccess(p0: String?) {
        FMLog.li("onSuccess")
        methodChannel.invokeMethod("themeInitSuccess", true)
    }

    override fun onFailure(p0: String?, p1: Int) {
        val dataMap = mapOf("themeInitErrorInfo" to FMErrorMsg.getErrorMsg(p1))
        methodChannel.invokeMethod("themeInitFailure", dataMap)
    }
}