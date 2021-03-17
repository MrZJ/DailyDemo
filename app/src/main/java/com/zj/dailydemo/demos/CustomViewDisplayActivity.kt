package com.zj.dailydemo.demos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zj.dailydemo.R
import com.zj.dailydemo.demos.views.PercentView

class CustomViewDisplayActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CustomViewDisplayActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //1、绿色公务金额展示View
//        setContentView(R.layout.sample_percnt_view)
//        findViewById<PercentView>(R.id.percentView).setMoney(250f, 300f)

    }
}