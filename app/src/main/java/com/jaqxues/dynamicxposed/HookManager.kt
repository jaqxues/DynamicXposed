package com.jaqxues.dynamicxposed

import android.app.Application
import android.content.Context
import android.os.Environment
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage
import timber.log.Timber
import java.io.File


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project DynamicXposed.<br>
 * Date: 24.04.20 - Time 14:18.
 */

const val APP_PKG = "com.instagram.android"
const val APP_NAME = "Instagram"
const val MOD_CLS = "com.marz.instaprefs.HookMethods"
const val MOD_NAME = "Instaprefs"

@Suppress("DEPRECATION")
class HookManager: IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != APP_PKG)
            return

        Timber.plant(object: Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                super.log(priority, tag, "[Dynamic Xposed] - $message", t)
            }
        })

        Timber.d("Opened $APP_NAME. Dynamically Loading $MOD_NAME...")
        Timber.d("Hooking onAttach")

        findAndHookMethod(Application::class.java, "attach", Context::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                Timber.d("Called onAttach. Loading Apk with provided App Context")

                try {
                    val classLoader = loadApk(File(Environment.getExternalStorageDirectory(), "DynamicXposedModule/Dynamic_Module.apk"), param.args[0] as Context)

                    val hookClass = classLoader.loadClass(MOD_CLS)
                    val hookObj = hookClass.newInstance()
                    hookObj as IXposedHookLoadPackage
                    hookClass.declaredMethods.find { it.name == "onAttachHook" }!!.invoke(hookObj, lpparam, param)
                    Timber.d("Called $MOD_NAME HookMethods#loadPackageParam! Dynamic Hook successful!")
                } catch (ex: Exception) {
                    Timber.e(ex)
                    return
                }
            }
        })
    }
}