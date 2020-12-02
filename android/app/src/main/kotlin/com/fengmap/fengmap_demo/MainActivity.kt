package com.fengmap.fengmap_demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fengmap.android.map.FMMapUpgradeInfo
import com.fengmap.android.map.event.OnFMMapInitListener
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.BasicMessageChannel

import kotlinx.android.synthetic.main.flutter_view_layout.*


class MainActivity : AppCompatActivity(), OnFMMapInitListener {
    private var flutterEngine: FlutterEngine? = null

    private val counter = 0
    private val CHANNEL = "increment"
    private val EMPTY_MESSAGE = ""
    private val PING = "ping"
    private val messageChannel: BasicMessageChannel<String>? = null


    private fun getArgsFromIntent(intent: Intent): Array<String>? {
        val args = ArrayList<String>()
        if (intent.getBooleanExtra("trace-startup", false)) {
            args.add("--trace-startup")
        }
        if (intent.getBooleanExtra("start-paused", false)) {
            args.add("--start-paused");
        }
        if (intent.getBooleanExtra("enable-dart-profiling", false)) {
            args.add("--enable-dart-profiling");
        }
        if (args.isNotEmpty()) {
            val argsArray = arrayOfNulls<String>(args.size)
            return args.toArray(argsArray)
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val argsFromIntent = getArgsFromIntent(intent)
        if (flutterEngine == null) {
            flutterEngine = FlutterEngine(this, argsFromIntent)
            flutterEngine!!.dartExecutor.executeDartEntrypoint(
                    DartExecutor.DartEntrypoint.createDefault())
        }
        setContentView(R.layout.flutter_view_layout)

        flutter_view.attachToFlutterEngine(flutterEngine!!)

        val fmMap = mapView.fmMap
        fmMap.setOnFMMapInitListener(this)
        fmMap.openMapById("10347",true)
    }

    override fun onMapInitSuccess(p0: String?) {

    }

    override fun onMapInitFailure(p0: String?, p1: Int) {

    }

    override fun onUpgrade(p0: FMMapUpgradeInfo?): Boolean {
        return false
    }
}
