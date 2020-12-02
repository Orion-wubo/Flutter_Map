package com.fengmap.fengmap_demo

import io.flutter.embedding.engine.plugins.FlutterPlugin


class FLMapViewPlugin : FlutterPlugin {

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        val binaryMessenger = binding.binaryMessenger
        binding.platformViewRegistry.registerViewFactory(NATIVE_CCTV_VIEW_TYPE_ID,
                FLMapViewFactory(binaryMessenger))
    }

    companion object {
        var NATIVE_CCTV_VIEW_TYPE_ID = "com.fengmap.fengmap_demo/flmapview" //原生控件对应的viewtypeid

        @JvmStatic
        fun registerWith(registry: io.flutter.plugin.common.PluginRegistry.Registrar) {
            registry.platformViewRegistry().registerViewFactory(NATIVE_CCTV_VIEW_TYPE_ID,
                    FLMapViewFactory(registry.messenger()))
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    }

}