package com.jaqxues.dynamicxposed

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import de.robv.android.xposed.IXposedHookLoadPackage
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.io.File

const val PERM_REQ_CODE = 0xcafe
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val listener = View.OnClickListener {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERM_REQ_CODE)
            }
            load_apk_classloader.setOnClickListener(listener)
        } else init()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERM_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                init()
            else
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show()
        }
    }

    private fun init() {
        load_apk_classloader.setOnClickListener {
            try {
                val classLoader =
                    loadApk(
                        File(Environment.getExternalStorageDirectory(), "DynamicXposedModule/Dynamic_Module.apk"),
                        this
                    )
                val aClass = classLoader.loadClass("com.marz.instaprefs.HookMethods")
                Toast.makeText(this, "Loaded the ${aClass.simpleName} class successfully!", Toast.LENGTH_LONG).show()
            } catch (ex: Exception) {
                Timber.e(ex)
                Toast.makeText(this, "Exception occurred...", Toast.LENGTH_LONG).show()
            }
        }
    }
}
