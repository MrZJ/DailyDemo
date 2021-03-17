package com.zj.dailydemo.demos.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

/**
 * 实际就是通过 InitializationProvider 创建的时候去获取InitializationProvider 在Manifest 中meta-data
 * 中注册的Initializer，通过创建Initializer实例去调用Initializer的create方法完成初始化操作
 * 也就是说 咱们的第三方库初始化的time是在InitializationProvider创建的时候（onCreate）
 * Application attachBaseContext ->TestStartUpInitializer.create(context)->Application onCreate
 */
class TestStartUpInitializer : Initializer<String> {
    //通过ContentProvider初始化第三方库
    override fun create(context: Context): String {
        Log.e("jianzhang", "我被初始化了！create")
        return ""
    }

    //dependencies()方法表示，当前的LitePalInitializer是否还依赖于其他的Initializer
    // ，如果有的话，就在这里进行配置，App Startup会保证先初始化依赖的Initializer
    // ，然后才会初始化当前的LitePalInitializer。
    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}