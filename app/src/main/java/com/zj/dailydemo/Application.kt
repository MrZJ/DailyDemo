package com.zj.dailydemo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import timber.log.Timber
import java.lang.ref.WeakReference

class Application : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.e("jianzhang","attachBaseContext")
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Log.e("jianzhang","onCreate")
        ACLifeCycleManager.init(this)
    }

    object ACLifeCycleManager {
        private var mCurrentActivity: WeakReference<Activity>? = null
        fun getCurrentActivity(): Activity? {
            return mCurrentActivity?.get()
        }

        fun init(context: Application) {
            context.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(
                    activity: Activity,
                    savedInstanceState: Bundle?
                ) {
                }

                override fun onActivityStarted(activity: Activity) {
                }

                override fun onActivityResumed(activity: Activity) {
                    mCurrentActivity = WeakReference(activity)
                }

                override fun onActivityPaused(activity: Activity) {
                }

                override fun onActivityStopped(activity: Activity) {
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                }

                override fun onActivityDestroyed(activity: Activity) {
                }
            })
        }
    }
}