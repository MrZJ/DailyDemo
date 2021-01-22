package com.zj.dailydemo.demos.material

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zj.dailydemo.databinding.ActivityShapeableImageBinding

class ShapeAbleImageActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ShapeAbleImageActivity::class.java))
        }
    }

    private val mBinding: ActivityShapeableImageBinding by lazy {
        ActivityShapeableImageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }

}