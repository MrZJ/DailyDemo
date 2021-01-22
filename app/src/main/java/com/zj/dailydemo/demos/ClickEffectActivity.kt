package com.zj.dailydemo.demos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zj.dailydemo.databinding.ActivityClickEffectBinding
import com.zj.dailydemo.databinding.ActivityClipChildrenBinding

/**
 * 点击效果
 */
class ClickEffectActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClickEffectActivity::class.java))
        }
    }

    private val mBinding by lazy {
        ActivityClickEffectBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}