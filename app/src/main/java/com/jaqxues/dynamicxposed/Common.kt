package com.jaqxues.dynamicxposed

import android.content.Context
import dalvik.system.DexClassLoader
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project DynamicXposed.<br>
 * Date: 24.04.20 - Time 14:46.
 */

object Common {
    fun loadApk(apk: File, context: Context): ClassLoader {
        if (!apk.exists())
            throw FileNotFoundException("File does not exist. Aborting Loading External APK")

        Timber.d("Loading APK on Path ${apk.absolutePath}")

        return DexClassLoader(
            apk.absolutePath,
            context.codeCacheDir.absolutePath,
            null, Common::class.java.classLoader
        )
    }

    fun plant() {
        Timber.plant(object: Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                super.log(priority, tag, "[Dynamic Xposed] - $message", t)
            }
        })
    }
}