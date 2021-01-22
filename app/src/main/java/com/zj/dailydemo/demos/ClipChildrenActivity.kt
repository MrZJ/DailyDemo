package com.zj.dailydemo.demos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.zj.dailydemo.R
import com.zj.dailydemo.databinding.ActivityClipChildrenBinding
import com.zj.dailydemo.databinding.ActivityClipTopPaddingBinding

/**
 * 实验一下clipToPadding属性 实现类似画廊的效果
 */
class ClipChildrenActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClipChildrenActivity::class.java))
        }
    }

    private val mBinding by lazy {
        ActivityClipChildrenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}