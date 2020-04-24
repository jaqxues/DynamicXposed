package com.jaqxues.dynamicxposed

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import timber.log.Timber


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project DynamicXposed.<br>
 * Date: 24.04.20 - Time 14:18.
 */

const val MOD_PKG = "com.marz.instaprefs"

class HookManager: IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam.packageName != "com.instagram.android")
            return

        Timber.plant(Timber.DebugTree())

        Timber.d("Opened Instagram. Dynamically Loading Instaprefs")

        val apkFileName
    }
}